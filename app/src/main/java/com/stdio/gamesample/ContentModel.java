package com.stdio.gamesample;

import android.graphics.Bitmap;

public class ContentModel {

    int questionSoundId;
    int imgId1;
    int imgId2;
    int imgId3;
    int imgId4;
    int rightAnswerButtonId;

    ContentModel(int questionSoundId,int imgId1, int imgId2, int imgId3, int imgId4, int rightAnswerButtonId) {
        this.questionSoundId = questionSoundId;
        this.imgId1 = imgId1;
        this.imgId2 = imgId2;
        this.imgId3 = imgId3;
        this.imgId4 = imgId4;
        this.rightAnswerButtonId = rightAnswerButtonId;
    }
}
