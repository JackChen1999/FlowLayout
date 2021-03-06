package com.github.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.github.flowlayout.util.Utils;

import java.util.ArrayList;
import java.util.List;
/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：FlowLayout
 * Package_Name：com.github.flowlayout
 * Version：1.0
 * time：2017/2/15 18:24
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Flowlayout52 extends ViewGroup {

	private int horizontolSpacing = Utils.dip2px(13);
	private int verticalSpacing = Utils.dip2px(13);
	private int width;
	private int useWidth=0;// 当前行使用的宽度
	private Line currentline;// 当前的行
	private List<Line> mLines=new ArrayList<Flowlayout52.Line>();

	public Flowlayout52(Context context) {
		super(context);
	}

	public Flowlayout52(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public Flowlayout52(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	// 测量当前控件Flowlayout
	// 父类是有义务测量每个孩子的
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// MeasureSpec.EXACTLY;
		// MeasureSpec.AT_MOST;
		// MeasureSpec.UNSPECIFIED;
		mLines.clear();
		currentline=null;
		useWidth=0;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);  //  获取当前父容器(Flowlayout)的模式
		width = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec)-getPaddingBottom()-getPaddingTop(); // 获取到宽和高
		int childeWidthMode;
		int childeHeightMode;
		//  为了测量每个孩子 需要指定每个孩子测量规则
		childeWidthMode=widthMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:widthMode;
		childeHeightMode=heightMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode;

		int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(width, childeWidthMode);
		int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(height, childeHeightMode);

		currentline=new Line();// 创建了第一行
		for(int i=0;i<getChildCount();i++)	{
			View child=getChildAt(i);
			// 测量每个孩子
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

			int measuredWidth = child.getMeasuredWidth();
			useWidth+=measuredWidth;// 让当前行加上使用的长度
			if(useWidth<=width){
				currentline.addChild(child);//这时候证明当前的孩子是可以放进当前的行里,放进去
				useWidth+=horizontolSpacing;
				if(useWidth>width){
					//换行
					newLine();
				}
			}else{
				//换行
				if(currentline.getChildCount()<1){
					currentline.addChild(child);  // 保证当前行里面最少有一个孩子
				}
				newLine();
			}

		}
		if(!mLines.contains(currentline)){
			mLines.add(currentline);// 添加最后一行
		}
		int  totalheight=0;
		for(Line line:mLines){
			totalheight+=line.getHeight();
		}
		totalheight+=verticalSpacing*(mLines.size()-1)+getPaddingTop()+getPaddingBottom();

		setMeasuredDimension(width+getPaddingLeft()+getPaddingRight(),resolveSize(totalheight, heightMeasureSpec));
	}

	private void newLine() {
		mLines.add(currentline);// 记录之前的行
		currentline=new Line(); // 创建新的一行
		useWidth=0;
	}

	private class Line{
		int height=0; //当前行的高度
		int lineWidth=0;
		private List<View> children=new ArrayList<View>();
		/**
		 * 添加一个孩子
		 * @param child
		 */
		public void addChild(View child) {
			children.add(child);
			if(child.getMeasuredHeight()>height){
				height=child.getMeasuredHeight();
			}
			lineWidth+=child.getMeasuredWidth();
		}
		public int getHeight() {
			return height;
		}
		/**
		 * 返回孩子的数量
		 * @return
		 */
		public int getChildCount() {
			return children.size();
		}
		public void layout(int l, int t) {
			lineWidth+=horizontolSpacing*(children.size()-1);
			int surplusChild=0;
			int surplus=width-lineWidth;
			if(surplus>0){
				surplusChild=surplus/children.size();
			}
			for(int i=0;i<children.size();i++){
				View child=children.get(i);
				//  getMeasuredWidth()   控件实际的大小
				// getWidth()  控件显示的大小
				child.layout(l, t, l+child.getMeasuredWidth()+surplusChild, t+child.getMeasuredHeight());
				l+=child.getMeasuredWidth()+surplusChild;
				l+=horizontolSpacing;
			}
		}

	}
	// 分配每个孩子的位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l+=getPaddingLeft();
		t+=getPaddingTop();
		for(int i=0;i<mLines.size();i++){
			Line line=mLines.get(i);
			line.layout(l,t);  //交给每一行去分配
			t+=line.getHeight()+verticalSpacing;
		}
	}

}
