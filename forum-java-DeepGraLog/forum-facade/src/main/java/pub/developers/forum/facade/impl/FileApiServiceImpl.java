package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.file.FileUploadImgRequest;
import pub.developers.forum.api.service.FileApiService;
import pub.developers.forum.app.manager.FileManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.FileValidator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/11/23
 * @desc
 **/
@Slf4j
@Service
public class FileApiServiceImpl implements FileApiService {

    @Resource
    private FileManager fileManager;

    @Override
    public ResultModel<String> uploadImg(FileUploadImgRequest request) {
        FileValidator.uploadImg(request);
        ResultModel rm = ResultModelUtil.success(fileManager.uploadImg(request));
        return rm;
    }
}
