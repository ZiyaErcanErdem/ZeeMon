package org.zee.app.zeemon.extensions.execution.model.dto;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ContentDto {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer sourceIndex;

    @Size(max = 255)
    private String txt1;

    @Size(max = 255)
    private String txt2;

    @Size(max = 255)
    private String txt3;

    @Size(max = 255)
    private String txt4;

    @Size(max = 255)
    private String txt5;

    @Size(max = 255)
    private String txt6;

    @Size(max = 255)
    private String txt7;

    @Size(max = 255)
    private String txt8;

    @Size(max = 255)
    private String txt9;

    @Size(max = 255)
    private String txt10;

    @Size(max = 255)
    private String txt11;

    @Size(max = 255)
    private String txt12;

    @Size(max = 255)
    private String txt13;

    @Size(max = 255)
    private String txt14;

    @Size(max = 255)
    private String txt15;

    @Size(max = 255)
    private String txt16;

    @Size(max = 255)
    private String txt17;

    @Size(max = 255)
    private String txt18;

    @Size(max = 255)
    private String txt19;

    @Size(max = 255)
    private String txt20;

    private BigDecimal num1;

    private BigDecimal num2;

    private BigDecimal num3;

    private BigDecimal num4;

    private BigDecimal num5;

    private BigDecimal num6;

    private BigDecimal num7;

    private BigDecimal num8;

    private BigDecimal num9;

    private BigDecimal num10;

    private BigDecimal num11;

    private BigDecimal num12;

    private BigDecimal num13;

    private BigDecimal num14;

    private BigDecimal num15;

    private BigDecimal num16;

    private BigDecimal num17;

    private BigDecimal num18;

    private BigDecimal num19;

    private BigDecimal num20;

    private Instant date1;

    private Instant date2;

    private Instant date3;

    private Instant date4;

    private Instant date5;

    private Instant date6;

    private Instant date7;

    private Instant date8;

    private Instant date9;

    private Instant date10;

    private Instant bool1;

    private Instant bool2;

    private Instant bool3;

    private Instant bool4;

    private Instant bool5;

    @NotNull
    private Long checkScriptId;

    @NotNull
    private Long flowId;

    @NotNull
    private Long taskId;

    @NotNull
    private Long taskExecutionId;

    @NotNull
    private Long flowExecutionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public ContentDto sourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public void setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getTxt1() {
        return txt1;
    }

    public ContentDto txt1(String txt1) {
        this.txt1 = txt1;
        return this;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public ContentDto txt2(String txt2) {
        this.txt2 = txt2;
        return this;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public String getTxt3() {
        return txt3;
    }

    public ContentDto txt3(String txt3) {
        this.txt3 = txt3;
        return this;
    }

    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }

    public String getTxt4() {
        return txt4;
    }

    public ContentDto txt4(String txt4) {
        this.txt4 = txt4;
        return this;
    }

    public void setTxt4(String txt4) {
        this.txt4 = txt4;
    }

    public String getTxt5() {
        return txt5;
    }

    public ContentDto txt5(String txt5) {
        this.txt5 = txt5;
        return this;
    }

    public void setTxt5(String txt5) {
        this.txt5 = txt5;
    }

    public String getTxt6() {
        return txt6;
    }

    public ContentDto txt6(String txt6) {
        this.txt6 = txt6;
        return this;
    }

    public void setTxt6(String txt6) {
        this.txt6 = txt6;
    }

    public String getTxt7() {
        return txt7;
    }

    public ContentDto txt7(String txt7) {
        this.txt7 = txt7;
        return this;
    }

    public void setTxt7(String txt7) {
        this.txt7 = txt7;
    }

    public String getTxt8() {
        return txt8;
    }

    public ContentDto txt8(String txt8) {
        this.txt8 = txt8;
        return this;
    }

    public void setTxt8(String txt8) {
        this.txt8 = txt8;
    }

    public String getTxt9() {
        return txt9;
    }

    public ContentDto txt9(String txt9) {
        this.txt9 = txt9;
        return this;
    }

    public void setTxt9(String txt9) {
        this.txt9 = txt9;
    }

    public String getTxt10() {
        return txt10;
    }

    public ContentDto txt10(String txt10) {
        this.txt10 = txt10;
        return this;
    }

    public void setTxt10(String txt10) {
        this.txt10 = txt10;
    }

    public String getTxt11() {
        return txt11;
    }

    public ContentDto txt11(String txt11) {
        this.txt11 = txt11;
        return this;
    }

    public void setTxt11(String txt11) {
        this.txt11 = txt11;
    }

    public String getTxt12() {
        return txt12;
    }

    public ContentDto txt12(String txt12) {
        this.txt12 = txt12;
        return this;
    }

    public void setTxt12(String txt12) {
        this.txt12 = txt12;
    }

    public String getTxt13() {
        return txt13;
    }

    public ContentDto txt13(String txt13) {
        this.txt13 = txt13;
        return this;
    }

    public void setTxt13(String txt13) {
        this.txt13 = txt13;
    }

    public String getTxt14() {
        return txt14;
    }

    public ContentDto txt14(String txt14) {
        this.txt14 = txt14;
        return this;
    }

    public void setTxt14(String txt14) {
        this.txt14 = txt14;
    }

    public String getTxt15() {
        return txt15;
    }

    public ContentDto txt15(String txt15) {
        this.txt15 = txt15;
        return this;
    }

    public void setTxt15(String txt15) {
        this.txt15 = txt15;
    }

    public String getTxt16() {
        return txt16;
    }

    public ContentDto txt16(String txt16) {
        this.txt16 = txt16;
        return this;
    }

    public void setTxt16(String txt16) {
        this.txt16 = txt16;
    }

    public String getTxt17() {
        return txt17;
    }

    public ContentDto txt17(String txt17) {
        this.txt17 = txt17;
        return this;
    }

    public void setTxt17(String txt17) {
        this.txt17 = txt17;
    }

    public String getTxt18() {
        return txt18;
    }

    public ContentDto txt18(String txt18) {
        this.txt18 = txt18;
        return this;
    }

    public void setTxt18(String txt18) {
        this.txt18 = txt18;
    }

    public String getTxt19() {
        return txt19;
    }

    public ContentDto txt19(String txt19) {
        this.txt19 = txt19;
        return this;
    }

    public void setTxt19(String txt19) {
        this.txt19 = txt19;
    }

    public String getTxt20() {
        return txt20;
    }

    public ContentDto txt20(String txt20) {
        this.txt20 = txt20;
        return this;
    }

    public void setTxt20(String txt20) {
        this.txt20 = txt20;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public ContentDto num1(BigDecimal num1) {
        this.num1 = num1;
        return this;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public ContentDto num2(BigDecimal num2) {
        this.num2 = num2;
        return this;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

    public BigDecimal getNum3() {
        return num3;
    }

    public ContentDto num3(BigDecimal num3) {
        this.num3 = num3;
        return this;
    }

    public void setNum3(BigDecimal num3) {
        this.num3 = num3;
    }

    public BigDecimal getNum4() {
        return num4;
    }

    public ContentDto num4(BigDecimal num4) {
        this.num4 = num4;
        return this;
    }

    public void setNum4(BigDecimal num4) {
        this.num4 = num4;
    }

    public BigDecimal getNum5() {
        return num5;
    }

    public ContentDto num5(BigDecimal num5) {
        this.num5 = num5;
        return this;
    }

    public void setNum5(BigDecimal num5) {
        this.num5 = num5;
    }

    public BigDecimal getNum6() {
        return num6;
    }

    public ContentDto num6(BigDecimal num6) {
        this.num6 = num6;
        return this;
    }

    public void setNum6(BigDecimal num6) {
        this.num6 = num6;
    }

    public BigDecimal getNum7() {
        return num7;
    }

    public ContentDto num7(BigDecimal num7) {
        this.num7 = num7;
        return this;
    }

    public void setNum7(BigDecimal num7) {
        this.num7 = num7;
    }

    public BigDecimal getNum8() {
        return num8;
    }

    public ContentDto num8(BigDecimal num8) {
        this.num8 = num8;
        return this;
    }

    public void setNum8(BigDecimal num8) {
        this.num8 = num8;
    }

    public BigDecimal getNum9() {
        return num9;
    }

    public ContentDto num9(BigDecimal num9) {
        this.num9 = num9;
        return this;
    }

    public void setNum9(BigDecimal num9) {
        this.num9 = num9;
    }

    public BigDecimal getNum10() {
        return num10;
    }

    public ContentDto num10(BigDecimal num10) {
        this.num10 = num10;
        return this;
    }

    public void setNum10(BigDecimal num10) {
        this.num10 = num10;
    }

    public BigDecimal getNum11() {
        return num11;
    }

    public ContentDto num11(BigDecimal num11) {
        this.num11 = num11;
        return this;
    }

    public void setNum11(BigDecimal num11) {
        this.num11 = num11;
    }

    public BigDecimal getNum12() {
        return num12;
    }

    public ContentDto num12(BigDecimal num12) {
        this.num12 = num12;
        return this;
    }

    public void setNum12(BigDecimal num12) {
        this.num12 = num12;
    }

    public BigDecimal getNum13() {
        return num13;
    }

    public ContentDto num13(BigDecimal num13) {
        this.num13 = num13;
        return this;
    }

    public void setNum13(BigDecimal num13) {
        this.num13 = num13;
    }

    public BigDecimal getNum14() {
        return num14;
    }

    public ContentDto num14(BigDecimal num14) {
        this.num14 = num14;
        return this;
    }

    public void setNum14(BigDecimal num14) {
        this.num14 = num14;
    }

    public BigDecimal getNum15() {
        return num15;
    }

    public ContentDto num15(BigDecimal num15) {
        this.num15 = num15;
        return this;
    }

    public void setNum15(BigDecimal num15) {
        this.num15 = num15;
    }

    public BigDecimal getNum16() {
        return num16;
    }

    public ContentDto num16(BigDecimal num16) {
        this.num16 = num16;
        return this;
    }

    public void setNum16(BigDecimal num16) {
        this.num16 = num16;
    }

    public BigDecimal getNum17() {
        return num17;
    }

    public ContentDto num17(BigDecimal num17) {
        this.num17 = num17;
        return this;
    }

    public void setNum17(BigDecimal num17) {
        this.num17 = num17;
    }

    public BigDecimal getNum18() {
        return num18;
    }

    public ContentDto num18(BigDecimal num18) {
        this.num18 = num18;
        return this;
    }

    public void setNum18(BigDecimal num18) {
        this.num18 = num18;
    }

    public BigDecimal getNum19() {
        return num19;
    }

    public ContentDto num19(BigDecimal num19) {
        this.num19 = num19;
        return this;
    }

    public void setNum19(BigDecimal num19) {
        this.num19 = num19;
    }

    public BigDecimal getNum20() {
        return num20;
    }

    public ContentDto num20(BigDecimal num20) {
        this.num20 = num20;
        return this;
    }

    public void setNum20(BigDecimal num20) {
        this.num20 = num20;
    }

    public Instant getDate1() {
        return date1;
    }

    public ContentDto date1(Instant date1) {
        this.date1 = date1;
        return this;
    }

    public void setDate1(Instant date1) {
        this.date1 = date1;
    }

    public Instant getDate2() {
        return date2;
    }

    public ContentDto date2(Instant date2) {
        this.date2 = date2;
        return this;
    }

    public void setDate2(Instant date2) {
        this.date2 = date2;
    }

    public Instant getDate3() {
        return date3;
    }

    public ContentDto date3(Instant date3) {
        this.date3 = date3;
        return this;
    }

    public void setDate3(Instant date3) {
        this.date3 = date3;
    }

    public Instant getDate4() {
        return date4;
    }

    public ContentDto date4(Instant date4) {
        this.date4 = date4;
        return this;
    }

    public void setDate4(Instant date4) {
        this.date4 = date4;
    }

    public Instant getDate5() {
        return date5;
    }

    public ContentDto date5(Instant date5) {
        this.date5 = date5;
        return this;
    }

    public void setDate5(Instant date5) {
        this.date5 = date5;
    }

    public Instant getDate6() {
        return date6;
    }

    public ContentDto date6(Instant date6) {
        this.date6 = date6;
        return this;
    }

    public void setDate6(Instant date6) {
        this.date6 = date6;
    }

    public Instant getDate7() {
        return date7;
    }

    public ContentDto date7(Instant date7) {
        this.date7 = date7;
        return this;
    }

    public void setDate7(Instant date7) {
        this.date7 = date7;
    }

    public Instant getDate8() {
        return date8;
    }

    public ContentDto date8(Instant date8) {
        this.date8 = date8;
        return this;
    }

    public void setDate8(Instant date8) {
        this.date8 = date8;
    }

    public Instant getDate9() {
        return date9;
    }

    public ContentDto date9(Instant date9) {
        this.date9 = date9;
        return this;
    }

    public void setDate9(Instant date9) {
        this.date9 = date9;
    }

    public Instant getDate10() {
        return date10;
    }

    public ContentDto date10(Instant date10) {
        this.date10 = date10;
        return this;
    }

    public void setDate10(Instant date10) {
        this.date10 = date10;
    }

    public Instant getBool1() {
        return bool1;
    }

    public ContentDto bool1(Instant bool1) {
        this.bool1 = bool1;
        return this;
    }

    public void setBool1(Instant bool1) {
        this.bool1 = bool1;
    }

    public Instant getBool2() {
        return bool2;
    }

    public ContentDto bool2(Instant bool2) {
        this.bool2 = bool2;
        return this;
    }

    public void setBool2(Instant bool2) {
        this.bool2 = bool2;
    }

    public Instant getBool3() {
        return bool3;
    }

    public ContentDto bool3(Instant bool3) {
        this.bool3 = bool3;
        return this;
    }

    public void setBool3(Instant bool3) {
        this.bool3 = bool3;
    }

    public Instant getBool4() {
        return bool4;
    }

    public ContentDto bool4(Instant bool4) {
        this.bool4 = bool4;
        return this;
    }

    public void setBool4(Instant bool4) {
        this.bool4 = bool4;
    }

    public Instant getBool5() {
        return bool5;
    }

    public ContentDto bool5(Instant bool5) {
        this.bool5 = bool5;
        return this;
    }

    public void setBool5(Instant bool5) {
        this.bool5 = bool5;
    }

    

    public Long getCheckScriptId() {
		return checkScriptId;
	}

	public void setCheckScriptId(Long checkScriptId) {
		this.checkScriptId = checkScriptId;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskExecutionId() {
		return taskExecutionId;
	}

	public void setTaskExecutionId(Long taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}

	public Long getFlowExecutionId() {
		return flowExecutionId;
	}

	public void setFlowExecutionId(Long flowExecutionId) {
		this.flowExecutionId = flowExecutionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDto)) {
            return false;
        }
        return id != null && id.equals(((ContentDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", flowId=" + getFlowId() +
            ", taskId=" + getTaskId() +
            ", flowExecutionId=" + getFlowExecutionId() +
            ", taskExecutionId=" + getTaskExecutionId() +
            ", checkScriptId=" + getCheckScriptId() +
            ", sourceIndex=" + getSourceIndex() +
            ", txt1='" + getTxt1() + "'" +
            ", txt2='" + getTxt2() + "'" +
            ", txt3='" + getTxt3() + "'" +
            ", txt4='" + getTxt4() + "'" +
            ", txt5='" + getTxt5() + "'" +
            ", txt6='" + getTxt6() + "'" +
            ", txt7='" + getTxt7() + "'" +
            ", txt8='" + getTxt8() + "'" +
            ", txt9='" + getTxt9() + "'" +
            ", txt10='" + getTxt10() + "'" +
            ", txt11='" + getTxt11() + "'" +
            ", txt12='" + getTxt12() + "'" +
            ", txt13='" + getTxt13() + "'" +
            ", txt14='" + getTxt14() + "'" +
            ", txt15='" + getTxt15() + "'" +
            ", txt16='" + getTxt16() + "'" +
            ", txt17='" + getTxt17() + "'" +
            ", txt18='" + getTxt18() + "'" +
            ", txt19='" + getTxt19() + "'" +
            ", txt20='" + getTxt20() + "'" +
            ", num1=" + getNum1() +
            ", num2=" + getNum2() +
            ", num3=" + getNum3() +
            ", num4=" + getNum4() +
            ", num5=" + getNum5() +
            ", num6=" + getNum6() +
            ", num7=" + getNum7() +
            ", num8=" + getNum8() +
            ", num9=" + getNum9() +
            ", num10=" + getNum10() +
            ", num11=" + getNum11() +
            ", num12=" + getNum12() +
            ", num13=" + getNum13() +
            ", num14=" + getNum14() +
            ", num15=" + getNum15() +
            ", num16=" + getNum16() +
            ", num17=" + getNum17() +
            ", num18=" + getNum18() +
            ", num19=" + getNum19() +
            ", num20=" + getNum20() +
            ", date1='" + getDate1() + "'" +
            ", date2='" + getDate2() + "'" +
            ", date3='" + getDate3() + "'" +
            ", date4='" + getDate4() + "'" +
            ", date5='" + getDate5() + "'" +
            ", date6='" + getDate6() + "'" +
            ", date7='" + getDate7() + "'" +
            ", date8='" + getDate8() + "'" +
            ", date9='" + getDate9() + "'" +
            ", date10='" + getDate10() + "'" +
            ", bool1='" + getBool1() + "'" +
            ", bool2='" + getBool2() + "'" +
            ", bool3='" + getBool3() + "'" +
            ", bool4='" + getBool4() + "'" +
            ", bool5='" + getBool5() + "'" +
            "}";
    }
}
