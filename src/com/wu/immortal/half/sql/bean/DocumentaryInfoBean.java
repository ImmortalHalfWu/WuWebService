package com.wu.immortal.half.sql.bean;


import com.wu.immortal.half.sql.bean.enums.DOCUMENTARY_TYPE;
import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;

public class DocumentaryInfoBean extends ScanInfoBean {

    private Integer positionRatio;
    private transient DOCUMENTARY_TYPE documentaryTypeEnum;
    private Integer documentaryType;

    public DocumentaryInfoBean(
            Integer id,
            Integer userId,
            String scanUrl,
            String tagName,
            Integer orderType,
            Integer frequency,
            Boolean canUser,
            Integer positionRatio,
            Integer documentaryType) {
        super(id, userId, scanUrl, tagName, orderType, frequency, canUser);
        this.positionRatio = positionRatio;
        this.documentaryTypeEnum = DOCUMENTARY_TYPE.valueOf(documentaryType);
        this.documentaryType = documentaryType;
    }

    public Integer getPositionRatio() {
        return positionRatio;
    }

    public DOCUMENTARY_TYPE getDocumentaryTypeEnum() {
        if (documentaryTypeEnum == null && documentaryType != null) {
            documentaryTypeEnum = DOCUMENTARY_TYPE.valueOf(documentaryType);
        }
        return documentaryTypeEnum;
    }

    public Integer getDocumentaryType() {
        return documentaryType;
    }



    public static DocumentaryInfoBean newInstance() {
        return new DocumentaryInfoBean(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }



    @Override
    public boolean checkNull() {
        return super.checkNull() || positionRatio == null || documentaryType == null;
    }

    public static DocumentaryInfoBean newInstance(
            Integer userId,
            String scanUrl,
            String tagName,
            ORDER_TYPE orderType,
            Integer frequency,
            Boolean canUser,
            Integer positionRatio,
            DOCUMENTARY_TYPE documentaryType) {

        return new DocumentaryInfoBean(
                null, userId, scanUrl,
                tagName, orderType.getCode(), frequency,
                canUser, positionRatio, documentaryType.getCode()
        );

    }

}
