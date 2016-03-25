package com.madsquare.android.madutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Image related utility class
 * Created by Ted
 */
public class ImageUtils {

    /**
     * 배트맵 라운드 처리하는 메소드
     */
    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * Drawable 이미지의 Uri를 가져오는 메소드
     */
    public static Uri getImageResourceUri(Context context, int imageName) {
        return Uri.parse("res://" + context.getResources().getResourcePackageName(imageName) + "/" + imageName);
    }

    /**
     * Fresco로 Blur Image 처리하는 메소드
     *
     * @param context context
     * @param uri     imgge uri
     * @param imgView SimpleDraweeView
     * @param radius  radius (0.0f ~ 25.0f)
     */
    public static void setBlurImage(final Context context, Uri uri, SimpleDraweeView imgView, final float radius) {
        try {
            Postprocessor redMeshPostprocessor = new BasePostprocessor() {
                @Override
                public String getName() {
                    return "redMeshPostprocessor";
                }

                @Override
                public void process(Bitmap bitmap) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {

                        final RenderScript rs = RenderScript.create(context);
                        final Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                                Allocation.USAGE_SCRIPT);
                        final Allocation output = Allocation.createTyped(rs, input.getType());
                        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                        script.setRadius(radius); // 0.0f ~ 25.0f
                        script.setInput(input);
                        script.forEach(output);
                        output.copyTo(bitmap);
                    }
                }
            };

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(redMeshPostprocessor)
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController)
                    Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(imgView.getController())
                                    // other setters as you need
                            .build();
            imgView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
