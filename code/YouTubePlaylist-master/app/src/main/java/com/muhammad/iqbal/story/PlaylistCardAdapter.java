package com.muhammad.iqbal.story;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoStatistics;
import com.muhammad.iqbal.story.model.PlaylistVideos;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.muhammad.iqbal.story.utils.Utils;
import com.muhammad.iqbal.story.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * A RecyclerView.Adapter subclass which adapts {@link Video}'s to CardViews.
 */
public class PlaylistCardAdapter extends RecyclerView.Adapter<PlaylistCardAdapter.ViewHolder> {
    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private final PlaylistVideos mPlaylistVideos;
    private final YouTubeRecyclerViewFragment.LastItemReachedListener mListener;
    private FragmentActivity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final TextView mTitleText;
        public final ImageView mThumbnailImage;

        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTitleText = (TextView) v.findViewById(R.id.video_title);
            mThumbnailImage = (ImageView) v.findViewById(R.id.video_thumbnail);
        }
    }

    public PlaylistCardAdapter(PlaylistVideos playlistVideos, YouTubeRecyclerViewFragment.LastItemReachedListener lastItemReachedListener, FragmentActivity activity) {
        mPlaylistVideos = playlistVideos;
        mListener = lastItemReachedListener;
        this.mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlaylistCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_card, parent, false);
        // populate the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mPlaylistVideos.size() == 0) {
            return;
        }

        final Video video = mPlaylistVideos.get(position);
        final VideoSnippet videoSnippet = video.getSnippet();

        holder.mTitleText.setText(videoSnippet.getTitle());

        // load the video thumbnail image
        Picasso.with(holder.mContext)
                .load(videoSnippet.getThumbnails().getHigh().getUrl())
                .placeholder(R.drawable.video_placeholder)
                .into(holder.mThumbnailImage);

        // set the click listener to play the video
        holder.mThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getId())));

                //VideoFragment videoFragment = new VideoFragment(video);
                //mActivity.getSupportFragmentManager().beginTransaction()
                 //       .replace(R.id.container, videoFragment)
                 //       .commit();
                //Toast.makeText(mActivity, "open video activity", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mActivity, VideoActivity.class);
                final VideoSnippet videoSnippet = video.getSnippet();
                final VideoContentDetails videoContentDetails = video.getContentDetails();
                final VideoStatistics videoStatistics = video.getStatistics();

                intent.putExtra("id", video.getId());
                intent.putExtra("title", videoSnippet.getTitle());
                intent.putExtra("description", videoSnippet.getDescription());
                intent.putExtra("url", videoSnippet.getThumbnails().getHigh().getUrl());
                intent.putExtra("duration", videoContentDetails.getDuration());
                intent.putExtra("view",videoStatistics.getViewCount().toString());
                intent.putExtra("like", videoStatistics.getLikeCount().toString());
                intent.putExtra("dislike", videoStatistics.getDislikeCount().toString());
                mActivity.startActivity(intent);
                //intent.putExtaStrin("title", video.getTitle());
                //videoActivityIntent.putExtra("video", video);

                holder.mContext.startActivity(intent);
                //Snackbar snackbar = Snackbar.make(mActivity, "Open Video Activity", Snackbar.LENGTH_LONG);
                //snackbar.show();
            }
        });

        if (mListener != null) {
            // get the next playlist page if we're at the end of the current page and we have another page to get
            final String nextPageToken = mPlaylistVideos.getNextPageToken();
            if (!Utils.isEmpty(nextPageToken) && position == mPlaylistVideos.size() - 1) {
                holder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onLastItem(position, nextPageToken);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylistVideos.size();
    }


}
