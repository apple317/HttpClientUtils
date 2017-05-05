package com.base.apple.demo.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;

@SuppressLint("AppCompatCustomView")
public class VerticalSeekBar extends SeekBar
{
  private boolean isInScrollingContainer = false;
  private boolean mIsDragging;
  private int mScaledTouchSlop;
  private float mTouchDownY;
  float mTouchProgressOffset;

  public VerticalSeekBar(Context paramContext)
  {
    super(paramContext);
  }

  public VerticalSeekBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public VerticalSeekBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mScaledTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
  }

  private void attemptClaimDrag()
  {
    ViewParent localViewParent = getParent();
    if (localViewParent != null)
      localViewParent.requestDisallowInterceptTouchEvent(true);
  }

  private void trackTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = getHeight();
    int j = getPaddingTop();
    int k = getPaddingBottom();
    int m = i - j - k;
    int n = (int)paramMotionEvent.getY();
    float f2 = 0.0F;
    float f1= 0.0F;
    if (n > i - k)
      f1 = 0.0F;
    while (true)
    {
      setProgress((int)(f2 + getMax() * f1));
      if (n < j)
      {
        f1 = 1.0F;
      }
      else
      {
        f1 = (m - n + j) / m;
        f2 = this.mTouchProgressOffset;
      }
    }
  }

  public boolean isInScrollingContainer()
  {
    return this.isInScrollingContainer;
  }

  protected void onDraw(Canvas paramCanvas)
  {
      paramCanvas.rotate(-90.0F);
      paramCanvas.translate(-getHeight(), 0.0F);
      super.onDraw(paramCanvas);
      return;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
      super.onMeasure(paramInt2, paramInt1);
      setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
      return;
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt2, paramInt1, paramInt4, paramInt3);
  }

  void onStartTrackingTouch()
  {
    this.mIsDragging = true;
  }

  void onStopTrackingTouch()
  {
    this.mIsDragging = false;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (!isEnabled())
      return false;
    switch (paramMotionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:

        if (isInScrollingContainer()) {
          this.mTouchDownY = paramMotionEvent.getY();
        } else {
          setPressed(true);
          invalidate();
          onStartTrackingTouch();
          trackTouchEvent(paramMotionEvent);
          attemptClaimDrag();
          onSizeChanged(getWidth(), getHeight(), 0, 0);
        }
        return true;
      case MotionEvent.ACTION_HOVER_MOVE:
        if (this.mIsDragging)
          trackTouchEvent(paramMotionEvent);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        if (Math.abs(paramMotionEvent.getY() - this.mTouchDownY) > this.mScaledTouchSlop) {
          setPressed(true);
          invalidate();
          onStartTrackingTouch();
          trackTouchEvent(paramMotionEvent);
          attemptClaimDrag();
        }
        return true;
      case MotionEvent.ACTION_POINTER_UP:
        if (this.mIsDragging) {
          trackTouchEvent(paramMotionEvent);
          onStopTrackingTouch();
          setPressed(false);
        }
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        invalidate();
        onStartTrackingTouch();
        trackTouchEvent(paramMotionEvent);
        onStopTrackingTouch();
        return true;
    }
    return false;
  }

  public void setInScrollingContainer(boolean paramBoolean)
  {
    this.isInScrollingContainer = paramBoolean;
  }

  public void setProgress(int paramInt)
  {
      super.setProgress(paramInt);
      onSizeChanged(getWidth(), getHeight(), 0, 0);
  }
}
