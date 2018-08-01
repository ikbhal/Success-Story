package com.muhammad.iqbal.story;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.api.services.youtube.model.Video;
import com.muhammad.iqbal.story.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class VideoActivity extends AppCompatActivity  implements RewardedVideoAdListener {
    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private Video video;
    private TextView mTitleText;
    private TextView mDescriptionText;
    private ImageView mThumbnailImage;
    private ImageView mShareIcon;
    private TextView mShareText;
    private TextView mDurationText;
    private TextView mViewCountText;
    private  TextView mLikeCountText;
    private  TextView mDislikeCountText;
    private RewardedVideoAd mRewardedVideoAd;
    private boolean rewardVideoView = false;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_video);
        dl = findViewById(R.id.youtube_activity);

        MobileAds.initialize(this, AdmobKey.getAppId());

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoActivity.this, "back clicked in vidoe activity", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitleText = (TextView) findViewById(R.id.video_title);
        mDescriptionText = (TextView) findViewById(R.id.video_description);
        mThumbnailImage = (ImageView) findViewById(R.id.video_thumbnail);
        mShareIcon = (ImageView) findViewById(R.id.video_share);
        mShareText = (TextView) findViewById(R.id.video_share_text);
        mDurationText = (TextView) findViewById(R.id.video_dutation_text);
        mViewCountText= (TextView) findViewById(R.id.video_view_count);
        mLikeCountText = (TextView) findViewById(R.id.video_like_count);
        mDislikeCountText = (TextView) findViewById(R.id.video_dislike_count);

        mTitleText.setText(intent.getStringExtra("title"));

        mDescriptionText.setText(intent.getStringExtra("description"));

        //et load the video thumbnail image
        Picasso.with(this)
                .load(intent.getStringExtra("url"))
                .placeholder(R.drawable.video_placeholder)
                .into(mThumbnailImage);

        // set the click listener to play the video
        mThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + getIntent().getStringExtra("id"))));
            }
        });

        // create and set the click listener for both the share icon and share text
        View.OnClickListener shareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mRewardedVideoAd.isLoaded() && !rewardVideoView) {
                    rewardVideoView = !rewardVideoView;
                    mRewardedVideoAd.show();
                    return;
                }

                rewardVideoView = !rewardVideoView;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + getIntent().getStringExtra("title") + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + getIntent().getStringExtra("id"));
                sendIntent.setType("text/plain");
            }
        };
        mShareIcon.setOnClickListener(shareClickListener);
        mShareText.setOnClickListener(shareClickListener);

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(AdmobKey.getRewardedVideoAdUnitId(), new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        this.dl.closeDrawers();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}
