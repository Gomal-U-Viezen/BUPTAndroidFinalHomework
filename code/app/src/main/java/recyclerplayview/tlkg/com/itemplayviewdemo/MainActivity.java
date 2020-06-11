package recyclerplayview.tlkg.com.itemplayviewdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private int cn=0;

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private VideoRecyclerAdapter mAdapter;

    private FrameLayout videoRootViewFl;

    private MyVideoView videoView;

    private FrameLayout fullScreen;

    private View lastView;

    private int videoPosition = -1;

    private List<VideoBean> videoBeanList = new ArrayList<>();

    private int[] imageIds = new int[]{R.drawable.hzw_1, R.drawable.hzw_2,
            R.drawable.hzw_3, R.drawable.hzw_4, R.drawable.hzw_5, R.drawable.hzw_6,
            R.drawable.hzw_7, R.drawable.hzw_8, R.drawable.hzw_9,R.drawable.hzw_10};

    private static String[] VI=new String[]{

            "http://jzvd.nathen.cn/video/1137e480-170bac9c523-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/e0bd348-170bac9c3b8-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/2f03c005-170bac9abac-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/7bf938c-170bac9c18a-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/47788f38-170bac9ab8a-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/2d6ffe8f-170bac9ab87-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/633e0ce-170bac9ab65-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/2d6ffe8f-170bac9ab87-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/51f7552c-170bac98718-0007-1823-c86-de200.mp4",
            "http://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4"

    };

    private  String VIDEO_PATH = VI[cn];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            supportActionBar.setTitle("");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        videoRootViewFl = (FrameLayout) findViewById(R.id.video_root_fl);
        fullScreen = (FrameLayout) findViewById(R.id.video_full_screen);
        mAdapter = new VideoRecyclerAdapter(videoBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void showVideo(View view, final String videoPath) {
        View v;
        removeVideoView();
        if (videoRootViewFl.getVisibility() == View.VISIBLE) {
            videoRootViewFl.removeAllViews();
            videoRootViewFl.setVisibility(View.GONE);
        }
        if (videoView == null) {
            videoView = new MyVideoView(MainActivity.this);
//            videoView.setListener(new MyVideoView.IFullScreenListener() {
//                @Override
//                public void onClickFull(boolean isFull) {
//                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
//                        fullScreen.setVisibility(View.VISIBLE);
//                        removeVideoView();
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                        fullScreen.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
//                        videoView.setVideoPath(VIDEO_PATH);
//                        videoView.start();
//                    } else {
//                        fullScreen.removeAllViews();
//                        fullScreen.setVisibility(View.GONE);
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                        if (lastView instanceof ViewGroup) {
//                            ((ViewGroup) lastView).addView(videoView);
//                        }
//                        videoView.setVideoPath(VIDEO_PATH);
//                        videoView.start();
//                    }
//
//                }
//            })
        }
        videoView.stop();
        v = view.findViewById(R.id.item_imageview);
        if (v != null) v.setVisibility(View.INVISIBLE);
        v = view.findViewById(R.id.item_image_play);
        if (v != null) v.setVisibility(View.INVISIBLE);
        v = view.findViewById(R.id.item_video_root_fl);
        if (v != null) {
            v.setVisibility(View.VISIBLE);
            FrameLayout fl = (FrameLayout) v;
            fl.removeAllViews();
            fl.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
            VIDEO_PATH = videoPath;
            videoView.setVideoPath(videoPath);
            videoView.start();
        }
        lastView = view;
    }

    private void removeVideoView() {
        View v;
        if (lastView != null) {
            v = lastView.findViewById(R.id.item_imageview);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_image_play);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_video_root_fl);
            if (v != null) {
                FrameLayout ll = (FrameLayout) v;
                ll.removeAllViews();
                v.setVisibility(View.GONE);
            }
        }
    }

    private void initData() {
        VideoBean videoBean;
        for (int i = 0; i < 100; i++) {
            videoBean = new VideoBean(imageIds[i % imageIds.length], VIDEO_PATH);
            VIDEO_PATH=VI[(++cn)%VI.length];
            videoBeanList.add(videoBean);
        }
    }

    private void initEvent() {
        mAdapter.setListener(new VideoRecyclerAdapter.OnClickPlayListener() {
            @Override
            public void onPlayClick(View view, String videoPath) {
                showVideo(view, videoPath);
            }
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (videoPosition == -1 || videoRootViewFl.getVisibility() != View.VISIBLE) {
                    return;
                }
                if (videoPosition == recyclerView.getChildAdapterPosition(view)) {
                    videoPosition = -1;
                    showVideo(view, VIDEO_PATH);
                    VIDEO_PATH=VI[(++cn)%VI.length];
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (videoView == null || videoRootViewFl.getVisibility() == View.VISIBLE) return;
                View v = view.findViewById(R.id.item_video_root_fl);
                if (v != null) {
                    FrameLayout fl = (FrameLayout) v;
                    videoPosition = recyclerView.getChildAdapterPosition(view);
                    if (fl.getChildCount() > 0) {
                        fl.removeAllViews();
                        int position = 0;
                        if (videoView.isPlaying()) {
                            position = videoView.getPosition();
                            videoView.stop();
                        }
                        videoRootViewFl.setVisibility(View.VISIBLE);
                        videoRootViewFl.removeAllViews();
                        lastView = videoRootViewFl;
                        videoRootViewFl.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
                        videoView.setVideoPath(VIDEO_PATH);
                        VIDEO_PATH=VI[(++cn)%VI.length];
                        videoView.start();
                        videoView.seekTo(position);
//                        if (videoView.isPause()) {
//                            videoView.resume();
//                        }
                    }
                    fl.setVisibility(View.GONE);
                }
                v = view.findViewById(R.id.item_imageview);
                if (v != null) {
                    if (v.getVisibility() != View.VISIBLE) {
                        v.setVisibility(View.VISIBLE);
                    }
                }
                v = view.findViewById(R.id.item_image_play);
                if (v != null) {
                    if (v.getVisibility() != View.VISIBLE) {
                        v.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (videoView != null) {
            videoView.stop();
        }
        super.onDestroy();
    }
}
