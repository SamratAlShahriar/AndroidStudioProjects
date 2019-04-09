package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.concurrent.ExecutionException;

import info.samratalshahriar.nayeem.finalproject.R;

public class CustomSwipeAdapterForWebImage extends PagerAdapter{
    private Context context;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapterForWebImage(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.swipe_image_form_web, container, false );
        final ImageView viewImage = itemView.findViewById(R.id.showSlideImage);


        Handler hand = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    AsynTaskForWebImage downImage = new AsynTaskForWebImage(activity);
                    Bitmap image;
                    image = downImage.execute("https://picsum.photos/300/200/?random").get();
                    // image = downImage.execute("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Flag-map_of_Bangladesh2.svg/1200px-Flag-map_of_Bangladesh2.svg.png").get();
                    viewImage.setImageBitmap(image);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        hand.postDelayed(run, 200);


       // viewImage.setImageResource(id[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
