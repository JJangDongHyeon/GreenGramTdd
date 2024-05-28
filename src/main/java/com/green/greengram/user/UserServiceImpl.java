package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int postUser(MultipartFile file , SignUpPostReq p){
        String hashedPassword = BCrypt.hashpw(p.getUpw() , BCrypt.gensalt());
        p.setUpw(hashedPassword);
        String filename = customFileUtils.makeRandomFileName(file);
        p.setPic(filename);
        int result = mapper.postUser(p);
        log.info("pic:{}" , p.getPic());
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

    public UserInfoGetRes getProfileUserInfo(UserInfoGetReq p){
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        String fileNm = customFileUtils.makeRandomFileName(p.getPic());
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        //기존 폴더 삭제
        try {
            String midPath = String.format("user/%d", p.getSignedUserId());
            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);

            customFileUtils.makeFolders(midPath);
            String filePath = String.format("%s/%s", midPath, fileNm);
            customFileUtils.transferTo(p.getPic(), filePath);
        } catch (Exception e) {
            throw new RuntimeException("사진 변경 오류");
        }
        return fileNm;
    }
}
