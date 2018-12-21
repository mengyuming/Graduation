package com.graduation.bean;

import org.apache.ibatis.type.Alias;

@Alias("index")
public class Index {
    private Integer id;
    //评价者
    private String pnumber;

    //被评者
    private String bnumber;

    //评价的日期
    private String times;

    //评价的课程信息
    private Integer cno;

    private String other;

    private Double q1;
    private Double q2;
    private Double q3;
    private Double q4;
    private Double q5;
    private Double q6;
    private Double q7;
    private Double q8;
    private Double q9;
    private Double q10;
    private Double q11;
    private Double q12;
    private Double q13;
    private Double q14;
    private Double q15;
    private Double q16;
    private Double q17;
    private Double q18;
    private Double q19;
    private Double q20;

    @Override
    public String toString() {
        return "Index{" +
                "id=" + id +
                ", pnumber='" + pnumber + '\'' +
                ", bnumber='" + bnumber + '\'' +
                ", times='" + times + '\'' +
                ", cno=" + cno +
                ", other='" + other + '\'' +
                ", q1=" + q1 +
                ", q2=" + q2 +
                ", q3=" + q3 +
                ", q4=" + q4 +
                ", q5=" + q5 +
                ", q6=" + q6 +
                ", q7=" + q7 +
                ", q8=" + q8 +
                ", q9=" + q9 +
                ", q10=" + q10 +
                ", q11=" + q11 +
                ", q12=" + q12 +
                ", q13=" + q13 +
                ", q14=" + q14 +
                ", q15=" + q15 +
                ", q16=" + q16 +
                ", q17=" + q17 +
                ", q18=" + q18 +
                ", q19=" + q19 +
                ", q20=" + q20 +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setQ1(Double q1) {
        this.q1 = q1;
    }

    public void setQ2(Double q2) {
        this.q2 = q2;
    }

    public void setQ3(Double q3) {
        this.q3 = q3;
    }

    public void setQ4(Double q4) {
        this.q4 = q4;
    }

    public void setQ5(Double q5) {
        this.q5 = q5;
    }

    public void setQ6(Double q6) {
        this.q6 = q6;
    }

    public void setQ7(Double q7) {
        this.q7 = q7;
    }

    public void setQ8(Double q8) {
        this.q8 = q8;
    }

    public void setQ9(Double q9) {
        this.q9 = q9;
    }

    public void setQ10(Double q10) {
        this.q10 = q10;
    }

    public void setQ11(Double q11) {
        this.q11 = q11;
    }

    public void setQ12(Double q12) {
        this.q12 = q12;
    }

    public void setQ13(Double q13) {
        this.q13 = q13;
    }

    public void setQ14(Double q14) {
        this.q14 = q14;
    }

    public void setQ15(Double q15) {
        this.q15 = q15;
    }

    public void setQ16(Double q16) {
        this.q16 = q16;
    }

    public void setQ17(Double q17) {
        this.q17 = q17;
    }

    public void setQ18(Double q18) {
        this.q18 = q18;
    }

    public void setQ19(Double q19) {
        this.q19 = q19;
    }

    public void setQ20(Double q20) {
        this.q20 = q20;
    }

    public Integer getId() {
        return id;
    }

    public String getPnumber() {
        return pnumber;
    }

    public String getBnumber() {
        return bnumber;
    }

    public String getTimes() {
        return times;
    }

    public Integer getCno() {
        return cno;
    }

    public String getOther() {
        return other;
    }

    public Double getQ1() {
        return q1;
    }

    public Double getQ2() {
        return q2;
    }

    public Double getQ3() {
        return q3;
    }

    public Double getQ4() {
        return q4;
    }

    public Double getQ5() {
        return q5;
    }

    public Double getQ6() {
        return q6;
    }

    public Double getQ7() {
        return q7;
    }

    public Double getQ8() {
        return q8;
    }

    public Double getQ9() {
        return q9;
    }

    public Double getQ10() {
        return q10;
    }

    public Double getQ11() {
        return q11;
    }

    public Double getQ12() {
        return q12;
    }

    public Double getQ13() {
        return q13;
    }

    public Double getQ14() {
        return q14;
    }

    public Double getQ15() {
        return q15;
    }

    public Double getQ16() {
        return q16;
    }

    public Double getQ17() {
        return q17;
    }

    public Double getQ18() {
        return q18;
    }

    public Double getQ19() {
        return q19;
    }

    public Double getQ20() {
        return q20;
    }
}
