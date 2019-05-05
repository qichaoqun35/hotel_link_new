package com.example.qichaoqun.amerilink.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.model.imodel.IMeModel;
import com.example.qichaoqun.amerilink.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qichaoqun
 * @date 2018/10/7
 */
public class MeModel implements IMeModel {
}
