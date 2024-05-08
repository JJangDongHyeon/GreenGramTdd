package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.SignInPostReq;
import com.green.greengram.user.model.SignInRes;
import com.green.greengram.user.model.SignUpPostReq;
import com.green.greengram.user.model.User;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int postUser(MultipartFile file , SignUpPostReq p){
        String hashedPassword = BCrypt.hashpw(p.getUpw() , BCrypt.gensalt());
        p.setUpw(hashedPassword);
        String filename = customFileUtils.makeRandomFileName(file);
        p.setPic(filename);
        int result = mapper.postUser(p);
        if(p.getPic() == null){
            return result;
        }
        try {
            String path = String.format("user/%d" , p.getUserId());
            customFileUtils.makeFolders(path);
            String target = String.format("%s/%s", path,filename);
            customFileUtils.transferTo(file,target);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("파일 오류");
        }
        return result;

    }

    public SignInRes postSignIn(SignInPostReq p){
       User user = mapper.getUserById(p.getUid());
       if(user == null){
           throw new RuntimeException("아이디를 확인해주세요");
       } else if (!BCrypt.checkpw(p.getUpw(), user.getUpw() )) {
           throw new RuntimeException("비밀번호를 확인해주세요");
       }
       return SignInRes.builder()
               .userId(user.getUserId())
               .nm(user.getNm())
               .pic(user.getPic()).build();
    }

}
