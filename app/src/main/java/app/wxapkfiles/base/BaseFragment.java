package app.wxapkfiles.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import app.common.util.ClassUtil;

/**
 * File: BaseFragment
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/3 11:19
 * Description:
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getBinding(container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected T getBinding(ViewGroup container) {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = ClassUtil.getRawType(type);
            Method method = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.class);
            return (T) method.invoke(null, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void initView();
}
