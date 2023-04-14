package com.example.social_media;

import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class Videosfragment extends RecyclerView.ViewHolder {

    public Videosfragment(@NonNull View itemView) {
        super(itemView);
    }
    public void SetVideo(FragmentActivity activity, String name , String url, String postUri, String time, String uid, String type, String desc){


        SimpleExoPlayer exoPlayer;
        PlayerView playerView =itemView.findViewById(R.id.exoplayer_item_post);
            try{
//                BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter.Builder(activity).build();
//                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//                exoPlayer =(SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(activity);
//                Uri video =Uri.parse(postUri);
//                DefaultHttpDataSourceFactory df =new DefaultHttpDataSourceFactory("video");
//                ExtractorsFactory ef = new DefaultExtractorsFactory();
//                MediaSource mediaSource =new ExtractorMediaSource(video,df,ef,null,null);
//                playerView.setPlayer(exoPlayer);
//                exoPlayer.prepare(mediaSource);
//                exoPlayer.setPlayWhenReady(false);

                ExoPlayer simpleExoPlayer = new ExoPlayer.Builder(activity).build();
                playerView.setPlayer(simpleExoPlayer);
                MediaItem mediaItem = MediaItem.fromUri(postUri);
                simpleExoPlayer.addMediaItem(mediaItem);
                simpleExoPlayer.prepare();
                simpleExoPlayer.setPlayWhenReady(false);

            }catch (Exception e){
                Toast.makeText(activity, "Error"+e, Toast.LENGTH_SHORT).show();
            }

        }

    }


