/*
  @authors Aaron David Tawil & Eldar Weiss
*/

package com.example.kufsa.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * This class defines the view animation for the reviews
 */
public class ViewAnimation {

    public static void expand(final View v, final AnimListener animListener) {
        Animation a = expandAction(v);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animListener.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    /**
     * This method starts the animation in the view
     *
     * @param v the view being used
     */
    public static void expand(final View v) {
        Animation a = expandAction(v);
        v.startAnimation(a);
    }

    /**
     * This method defines the animation settings
     *
     * @param v the view being used
     */
    private static Animation expandAction(final View v) {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            /**
             * This method applies the animation's transformation
             * @param interpolatedTime amount of seconds for transformation
             * @param t the transformation object
             */
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            /**
             * This method defines bounds
             * @return true if bounds changed, false otherwise
             */
            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        return a;
    }

    /**
     * This method collapses the animation settings
     *
     * @param v the view being used
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            /**
             * This method defines the animation transformation settings
             * @param interpolatedTime time it takes for animation
             * @param t defines the transformation itself
             */
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            /**
             * THis method defines if to change bounds
             * @return bool true if bounds changed, false otherwise
             */
            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * This method sets up part of the שnimation
     *
     * @param v            view being used
     * @param animListener animation listener
     */
    public static void flyInDown(final View v, final AnimListener animListener) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0.0f);
        v.setTranslationY(0);
        v.setTranslationY(-v.getHeight());
        // Prepare the View for the animation
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1.0f)
                .start();
    }

    /**
     * This method sets up part of the animation
     *
     * @param v            view being used
     * @param animListener animation listener
     */
    public static void flyOutDown(final View v, final AnimListener animListener) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1.0f);
        v.setTranslationY(0);
        // Prepare the View for the animation
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(0.0f)
                .start();
    }

    /**
     * This method defines the animation fading in
     *
     * @param v the view being used
     */
    public static void fadeIn(final View v) {
        ViewAnimation.fadeIn(v, null);
    }

    /**
     * This method defines the fade in of the animation
     *
     * @param v the view being used
     */
    public static void fadeIn(final View v, final AnimListener animListener) {
        v.setVisibility(View.GONE);
        v.setAlpha(0.0f);
        // Prepare the View for the animation
        v.animate()
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1.0f);
    }

    /**
     * This method defines the fade out of the animation
     *
     * @param v the view being used
     */
    public static void fadeOut(final View v) {
        ViewAnimation.fadeOut(v, null);
    }

    /**
     * This method defines the fade out of the animation
     *
     * @param v            the view being used
     * @param animListener the listener for the animation to fade out
     */
    public static void fadeOut(final View v, final AnimListener animListener) {
        v.setAlpha(1.0f);
        // Prepare the View for the animation
        v.animate()
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(0.0f);
    }

    /**
     * This method defines the show in of the animation
     *
     * @param v the view being used
     */
    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }

    /**
     * This method helps defines the show out of the animation
     *
     * @param v the view being used
     */
    public static void initShowOut(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }

    /**
     * This method defines the show out of the animation
     *
     * @param v the view being used
     */
    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    /**
     * This method defines sets the rotation for the animation
     *
     * @param v      the view being used
     * @param rotate bollean for rotation
     * @return true if rotated
     */
    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return rotate;
    }

    /**
     * This method defines the fade out in
     *
     * @param view the view being used
     */
    public static void fadeOutIn(View view) {
        view.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.f, 0.5f, 1.f);
        ObjectAnimator.ofFloat(view, "alpha", 0.f).start();
        animatorAlpha.setDuration(500);
        animatorSet.play(animatorAlpha);
        animatorSet.start();
    }

    /**
     * This method shows the scale
     *
     * @param v the view being sued
     */
    public static void showScale(final View v) {
        ViewAnimation.showScale(v, null);
    }

    /**
     * This method runs the scale definitions
     *
     * @param v            the view being used
     * @param animListener the listener for the animation
     */
    public static void showScale(final View v, final AnimListener animListener) {
        v.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    /**
     * This method hides the scale
     *
     * @param v the view being sued
     */
    public static void hideScale(final View v) {
        ViewAnimation.fadeOut(v, null);
    }

    /**
     * This method defines hiding the scale
     *
     * @param v            the view being used
     * @param animListener the listener for the animation
     */
    public static void hideScale(final View v, final AnimListener animListener) {
        v.animate()
                .scaleY(0)
                .scaleX(0)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    /**
     * This method hides the fab
     *
     * @param fab the view being sued
     */
    public static void hideFab(View fab) {
        int moveY = 2 * fab.getHeight();
        fab.animate()
                .translationY(moveY)
                .setDuration(300)
                .start();
    }

    /**
     * This method showes the fab
     *
     * @param fab the view being sued
     */
    public static void showFab(View fab) {
        fab.animate()
                .translationY(0)
                .setDuration(300)
                .start();
    }

    /**
     * This method is the interface for the animation listener
     */
    public interface AnimListener {
        void onFinish();
    }
}
