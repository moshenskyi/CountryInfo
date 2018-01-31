package com.example.nazarii_moshenskyi.multithreadingtask.presentation.main.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.nazarii_moshenskyi.multithreadingtask.presentation.main.view.MainMvpView;
import com.example.nazarii_moshenskyi.multithreadingtask.presentation.threads.AsyncTaskDataLoader;
import com.example.nazarii_moshenskyi.multithreadingtask.presentation.threads.FileHandlerThread;

public class MainPresenter implements MainMvpPresenter {
    interface OnComplete {
        void done();
    }

    private static final String NAME = "name: ";
    private static final String PHONE = "phone: ";
    private static final String EMAIL = "email: ";
    private static final String NEW_LINE = "\n";

    private static final String FILE_HANDLER_THREAD_NAME = "FileHandlerThread";

    private static final int DELAY = 3000;
    private FileHandlerThread handlerThread;


    private MainMvpView view;
    private AsyncTaskDataLoader loader;

    @Override
    public void attachView(final MainMvpView view) {
        this.view = view;
        view.showLoading();
        loadData(new OnComplete() {
            @Override
            public void done() {
                view.hideLoading();
            }
        });

    }

    private void loadData(final OnComplete onComplete) {
        handlerThread = new FileHandlerThread(FILE_HANDLER_THREAD_NAME);
        handlerThread.start();
        handlerThread.prepareHandler();
        handlerThread.postTask(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.readData();
                    try {
                        Thread.sleep(DELAY);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (view != null) {
                                    onComplete.done();
                                }
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handlerThread.quit();
            }
        });

    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }

        if (loader != null && !loader.isCancelled()) {
            loader.cancel(true);
        }
    }

    @Override
    public void runHandlerThread(final String[] data) {
        if (view != null) {
            if (isValid(data)) {
                view.showLoading();
                handlerThread = new FileHandlerThread(FILE_HANDLER_THREAD_NAME);
                handlerThread.start();
                handlerThread.prepareHandler();
                handlerThread.postTask(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.writeToFile(transformData(data));
                        }
                        try {
                            Thread.sleep(DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handlerThread.quit();
                    }
                });
                view.hideLoading();
            } else {
                view.noDataFound();
            }
        }
    }

    @Override
    public void runAsyncTask(String[] data) {
        if (view != null) {
            if (isValid(data)) {
                view.showLoading();
                loader = new AsyncTaskDataLoader(view);
                loader.execute(transformData(data));
                view.hideLoading();
            } else {
                view.noDataFound();
            }
        }
    }

    private String transformData(String[] data) {
        return new StringBuilder().append(NAME).append(data[0]).append(NEW_LINE)
                .append(PHONE).append(data[1]).append(NEW_LINE)
                .append(EMAIL).append(data[2]).append(NEW_LINE).toString();
    }

    private boolean isValid(String[] data) {
        for (String item : data) {
            if (item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
