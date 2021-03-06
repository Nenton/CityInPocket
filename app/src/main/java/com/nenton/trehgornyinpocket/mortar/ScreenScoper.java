package com.nenton.trehgornyinpocket.mortar;

import android.support.annotation.Nullable;
import android.util.Log;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mortar.MortarScope;

public class ScreenScoper {
    private static final String TAG = "Screen Scooper";
    private static Map<String, MortarScope> sScopeMap = new HashMap<>();

    private ScreenScoper() {
        throw new IllegalStateException("Utility class");
    }

    public static MortarScope getScreenScope(AbstractScreen screen) {
        if (!sScopeMap.containsKey(screen.getScopeName())) {
            Log.e(TAG, " getScreenScope: create new scope");
            return createScreenScope(screen);
        } else {
            Log.e(TAG, " getScreenScope: return has scope");
            return sScopeMap.get(screen.getScopeName());
        }
    }

    public static void registerScope(MortarScope scope) {
        sScopeMap.put(scope.getName(), scope);
    }

    private static void cleanScopeMap() {
        Iterator<Map.Entry<String, MortarScope>> iterator = sScopeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MortarScope> entry = iterator.next();
            if (entry.getValue().isDestroyed()) {
                iterator.remove();
            }
        }
    }

    public static void destroyScreenScope(String scopeName) {
        MortarScope mortarScope = sScopeMap.get(scopeName);
        mortarScope.destroy();
        cleanScopeMap();
    }

    @Nullable
    private static String getParentScopeName(AbstractScreen screen) {
        try {
            String genericName = ((Class) ((ParameterizedType) screen.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
            String parentScopeName = genericName;
            if (parentScopeName.contains("$")) {
                parentScopeName = parentScopeName.substring(0, genericName.indexOf('$'));
            }
            return parentScopeName;
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
            return null;
        }
    }

    private static MortarScope createScreenScope(AbstractScreen screen) {
        Log.e(TAG, " createScreenScope: scope name: " + screen.getScopeName());
        String parentScopeName = getParentScopeName(screen);
        MortarScope parentScope = sScopeMap.get(parentScopeName);
        Object screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME));
        MortarScope newScope = parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, screenComponent)
                .build(screen.getScopeName());
        registerScope(newScope);
        return newScope;
    }
}
