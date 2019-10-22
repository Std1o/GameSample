package com.stdio.gamesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3, imageView4;
    MediaPlayer mPlayer;
    ArrayList<ContentModel> contentModelList;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findImageViewsById();
        getData();
        setViewsContent();
    }

    private void findImageViewsById () {
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
    }

    private int getRandomNumber (int limit){
        Random randomNumber = new Random();
        return randomNumber.nextInt(limit);
    }

    protected void setViewsContent() {
        id = getRandomNumber(5);
        playSound(contentModelList.get(id).questionSoundId);
        Glide.with(this).load(contentModelList.get(id).imgId1).into(imageView1);
        Glide.with(this).load(contentModelList.get(id).imgId2).into(imageView2);
        Glide.with(this).load(contentModelList.get(id).imgId3).into(imageView3);
        Glide.with(this).load(contentModelList.get(id).imgId4).into(imageView4);
    }

    protected void getData() {
        contentModelList = new ArrayList();
        contentModelList.clear();
        contentModelList.add(new ContentModel(R.raw.bear, R.drawable.img4, R.drawable.img9, R.drawable.img10, R.drawable.img16, R.id.imageView3));
        contentModelList.add(new ContentModel(R.raw.dog, R.drawable.img17, R.drawable.img18, R.drawable.img11, R.drawable.img12, R.id.imageView1));
        contentModelList.add(new ContentModel(R.raw.lion, R.drawable.img5, R.drawable.img1, R.drawable.img14, R.drawable.img20, R.id.imageView2));
        contentModelList.add(new ContentModel(R.raw.raccoon, R.drawable.img6, R.drawable.img7, R.drawable.img13, R.drawable.img15, R.id.imageView4));
        contentModelList.add(new ContentModel(R.raw.wolf, R.drawable.img19, R.drawable.img2, R.drawable.img18, R.drawable.img8, R.id.imageView3));
    }

    public void onClick (View view){
        if (view.getId() == contentModelList.get(id).rightAnswerButtonId) {
            playSound(R.raw.answer_correct);
            waitingToCreateNextQuestion();
        }
        else {

        }
    }

    private void playSound (int soundId) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }
        mPlayer = MediaPlayer.create(this, soundId);
        mPlayer.start();
    }

    private void waitingToCreateNextQuestion() {
        NewTask newTask = new NewTask();
        newTask.execute();
    }

    class NewTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setViewsContent();
        }
    }
}
