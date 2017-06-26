package tinker.example.gaox.tinkertest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;

/**
 * Author gaox on 2017/6/26.
 */
@DefaultLifeCycle(application = "tinker.example.gaox.tinkertest.MyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL
)
public class MyApplicationLike extends DefaultApplicationLike {

    private static final String TAG = "MyApplication";
    private ApplicationLike tinkerApplicationLike;

    public MyApplicationLike(Application application, int i, boolean b, long l, long l1, Intent intent) {
        super(application, i, b, l, l1, intent);
    }
    @Override
    public void onBaseContextAttached(Context context) {
        super.onBaseContextAttached(context);
        MultiDex.install(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.TINKER_ENABLE) {
            TinkerPatch.init(this)
                    .reflectPatchLibrary()
                    .fetchPatchUpdate(true)
//                    .setFetchPatchIntervalByHours(3)
//                    .setFetchDynamicConfigIntervalByHours(3)
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setPatchResultCallback(new ResultCallBack() {
                        @Override
                        public void onPatchResult(PatchResult patchResult) {
                            Log.e("==Tinker补丁成功", patchResult.toString());
                        }
                    });
        }
    }

}
