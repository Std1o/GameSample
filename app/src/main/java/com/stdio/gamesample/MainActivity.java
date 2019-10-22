package com.stdio.gamesample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3, imageView4;
    MediaPlayer mPlayer;
    ArrayList<ContentModel> contentModelList;
    ArrayList <Integer> antiClone = new ArrayList<>();
    int id = 0;
    Dialog imageDialog;

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
        int elementId = randomNumber.nextInt(limit);
        if (antiClone.contains(elementId)){
            elementId = getRandomNumber(limit);
        }
        else{
            antiClone.add(elementId);
        }
        return elementId;
    }

    protected void setViewsContent() {
        if (contentModelList.size() != antiClone.size()) {
            id = getRandomNumber(5);
            playSound(contentModelList.get(id).questionSoundId);
            Glide.with(this).load(contentModelList.get(id).imgId1).into(imageView1);
            Glide.with(this).load(contentModelList.get(id).imgId2).into(imageView2);
            Glide.with(this).load(contentModelList.get(id).imgId3).into(imageView3);
            Glide.with(this).load(contentModelList.get(id).imgId4).into(imageView4);
        }
        else {
            Toast.makeText(this, "That's all", Toast.LENGTH_SHORT).show();
        }
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
            startImageDialog();
        }
        else {
            playSound(R.raw.wrong_answer);
        }
    }

    private void startImageDialog() {
        imageDialog = new Dialog(this, R.style.edit_AlertDialog_style);
        imageDialog.setContentView(R.layout.activity_start_dialog);


        ImageView imageView = (ImageView) imageDialog.findViewById(R.id.start_img);
        imageView.setImageDrawable(((ImageView)findViewById(contentModelList.get(id).rightAnswerButtonId)).getDrawable());
        imageDialog.show();

        imageDialog.setCanceledOnTouchOutside(true); // Sets whether this dialog is
        Window w = imageDialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        imageDialog.onWindowAttributesChanged(lp);
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
                Thread.sleep(1000);
                imageDialog.cancel();
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
