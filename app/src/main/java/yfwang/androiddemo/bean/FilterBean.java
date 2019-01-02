package yfwang.androiddemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ShineF on 2017/7/11 0011.
 * data 必须继承Parcelable
 */

public class FilterBean<T> implements Parcelable {

    private boolean isSelect;
    private String text;
    private Object data;

    public FilterBean() {

    }

    public FilterBean(boolean isSelect, String text) {
        this.isSelect = isSelect;
        this.text = text;
    }

    public FilterBean(boolean isSelect, String text, T data) {
        this.isSelect = isSelect;
        this.text = text;
        this.data = data;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.text);

        if (this.data instanceof Serializable) {
            dest.writeSerializable((Serializable) this.data);
        } else if (this.data instanceof Parcelable) {
            dest.writeParcelable((Parcelable) this.data, flags);
        } else {

        }
    }

    protected FilterBean(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.text = in.readString();

        if (this.getData() instanceof Serializable) {
            this.data = in.readSerializable();
        } else if (this.data instanceof Parcelable) {
            this.data = in.readParcelable(Object.class.getClassLoader());
        } else {

        }
    }

    public static final Creator<FilterBean> CREATOR = new Creator<FilterBean>() {
        @Override
        public FilterBean createFromParcel(Parcel source) {
            return new FilterBean(source);
        }

        @Override
        public FilterBean[] newArray(int size) {
            return new FilterBean[size];
        }
    };
}
