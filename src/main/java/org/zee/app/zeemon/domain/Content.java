package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "source_index", nullable = false)
    private Integer sourceIndex;

    @Size(max = 255)
    @Column(name = "txt_1", length = 255)
    private String txt1;

    @Size(max = 255)
    @Column(name = "txt_2", length = 255)
    private String txt2;

    @Size(max = 255)
    @Column(name = "txt_3", length = 255)
    private String txt3;

    @Size(max = 255)
    @Column(name = "txt_4", length = 255)
    private String txt4;

    @Size(max = 255)
    @Column(name = "txt_5", length = 255)
    private String txt5;

    @Size(max = 255)
    @Column(name = "txt_6", length = 255)
    private String txt6;

    @Size(max = 255)
    @Column(name = "txt_7", length = 255)
    private String txt7;

    @Size(max = 255)
    @Column(name = "txt_8", length = 255)
    private String txt8;

    @Size(max = 255)
    @Column(name = "txt_9", length = 255)
    private String txt9;

    @Size(max = 255)
    @Column(name = "txt_10", length = 255)
    private String txt10;

    @Size(max = 255)
    @Column(name = "txt_11", length = 255)
    private String txt11;

    @Size(max = 255)
    @Column(name = "txt_12", length = 255)
    private String txt12;

    @Size(max = 255)
    @Column(name = "txt_13", length = 255)
    private String txt13;

    @Size(max = 255)
    @Column(name = "txt_14", length = 255)
    private String txt14;

    @Size(max = 255)
    @Column(name = "txt_15", length = 255)
    private String txt15;

    @Size(max = 255)
    @Column(name = "txt_16", length = 255)
    private String txt16;

    @Size(max = 255)
    @Column(name = "txt_17", length = 255)
    private String txt17;

    @Size(max = 255)
    @Column(name = "txt_18", length = 255)
    private String txt18;

    @Size(max = 255)
    @Column(name = "txt_19", length = 255)
    private String txt19;

    @Size(max = 255)
    @Column(name = "txt_20", length = 255)
    private String txt20;

    @Column(name = "num_1", precision = 21, scale = 2)
    private BigDecimal num1;

    @Column(name = "num_2", precision = 21, scale = 2)
    private BigDecimal num2;

    @Column(name = "num_3", precision = 21, scale = 2)
    private BigDecimal num3;

    @Column(name = "num_4", precision = 21, scale = 2)
    private BigDecimal num4;

    @Column(name = "num_5", precision = 21, scale = 2)
    private BigDecimal num5;

    @Column(name = "num_6", precision = 21, scale = 2)
    private BigDecimal num6;

    @Column(name = "num_7", precision = 21, scale = 2)
    private BigDecimal num7;

    @Column(name = "num_8", precision = 21, scale = 2)
    private BigDecimal num8;

    @Column(name = "num_9", precision = 21, scale = 2)
    private BigDecimal num9;

    @Column(name = "num_10", precision = 21, scale = 2)
    private BigDecimal num10;

    @Column(name = "num_11", precision = 21, scale = 2)
    private BigDecimal num11;

    @Column(name = "num_12", precision = 21, scale = 2)
    private BigDecimal num12;

    @Column(name = "num_13", precision = 21, scale = 2)
    private BigDecimal num13;

    @Column(name = "num_14", precision = 21, scale = 2)
    private BigDecimal num14;

    @Column(name = "num_15", precision = 21, scale = 2)
    private BigDecimal num15;

    @Column(name = "num_16", precision = 21, scale = 2)
    private BigDecimal num16;

    @Column(name = "num_17", precision = 21, scale = 2)
    private BigDecimal num17;

    @Column(name = "num_18", precision = 21, scale = 2)
    private BigDecimal num18;

    @Column(name = "num_19", precision = 21, scale = 2)
    private BigDecimal num19;

    @Column(name = "num_20", precision = 21, scale = 2)
    private BigDecimal num20;

    @Column(name = "date_1")
    private Instant date1;

    @Column(name = "date_2")
    private Instant date2;

    @Column(name = "date_3")
    private Instant date3;

    @Column(name = "date_4")
    private Instant date4;

    @Column(name = "date_5")
    private Instant date5;

    @Column(name = "date_6")
    private Instant date6;

    @Column(name = "date_7")
    private Instant date7;

    @Column(name = "date_8")
    private Instant date8;

    @Column(name = "date_9")
    private Instant date9;

    @Column(name = "date_10")
    private Instant date10;

    @Column(name = "bool_1")
    private Instant bool1;

    @Column(name = "bool_2")
    private Instant bool2;

    @Column(name = "bool_3")
    private Instant bool3;

    @Column(name = "bool_4")
    private Instant bool4;

    @Column(name = "bool_5")
    private Instant bool5;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contents")
    private CheckScript checkScript;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contents")
    private Flow flow;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contents")
    private Task task;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contents")
    private TaskExecution taskExecution;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contents")
    private FlowExecution flowExecution;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public Content sourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public void setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getTxt1() {
        return txt1;
    }

    public Content txt1(String txt1) {
        this.txt1 = txt1;
        return this;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public Content txt2(String txt2) {
        this.txt2 = txt2;
        return this;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public String getTxt3() {
        return txt3;
    }

    public Content txt3(String txt3) {
        this.txt3 = txt3;
        return this;
    }

    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }

    public String getTxt4() {
        return txt4;
    }

    public Content txt4(String txt4) {
        this.txt4 = txt4;
        return this;
    }

    public void setTxt4(String txt4) {
        this.txt4 = txt4;
    }

    public String getTxt5() {
        return txt5;
    }

    public Content txt5(String txt5) {
        this.txt5 = txt5;
        return this;
    }

    public void setTxt5(String txt5) {
        this.txt5 = txt5;
    }

    public String getTxt6() {
        return txt6;
    }

    public Content txt6(String txt6) {
        this.txt6 = txt6;
        return this;
    }

    public void setTxt6(String txt6) {
        this.txt6 = txt6;
    }

    public String getTxt7() {
        return txt7;
    }

    public Content txt7(String txt7) {
        this.txt7 = txt7;
        return this;
    }

    public void setTxt7(String txt7) {
        this.txt7 = txt7;
    }

    public String getTxt8() {
        return txt8;
    }

    public Content txt8(String txt8) {
        this.txt8 = txt8;
        return this;
    }

    public void setTxt8(String txt8) {
        this.txt8 = txt8;
    }

    public String getTxt9() {
        return txt9;
    }

    public Content txt9(String txt9) {
        this.txt9 = txt9;
        return this;
    }

    public void setTxt9(String txt9) {
        this.txt9 = txt9;
    }

    public String getTxt10() {
        return txt10;
    }

    public Content txt10(String txt10) {
        this.txt10 = txt10;
        return this;
    }

    public void setTxt10(String txt10) {
        this.txt10 = txt10;
    }

    public String getTxt11() {
        return txt11;
    }

    public Content txt11(String txt11) {
        this.txt11 = txt11;
        return this;
    }

    public void setTxt11(String txt11) {
        this.txt11 = txt11;
    }

    public String getTxt12() {
        return txt12;
    }

    public Content txt12(String txt12) {
        this.txt12 = txt12;
        return this;
    }

    public void setTxt12(String txt12) {
        this.txt12 = txt12;
    }

    public String getTxt13() {
        return txt13;
    }

    public Content txt13(String txt13) {
        this.txt13 = txt13;
        return this;
    }

    public void setTxt13(String txt13) {
        this.txt13 = txt13;
    }

    public String getTxt14() {
        return txt14;
    }

    public Content txt14(String txt14) {
        this.txt14 = txt14;
        return this;
    }

    public void setTxt14(String txt14) {
        this.txt14 = txt14;
    }

    public String getTxt15() {
        return txt15;
    }

    public Content txt15(String txt15) {
        this.txt15 = txt15;
        return this;
    }

    public void setTxt15(String txt15) {
        this.txt15 = txt15;
    }

    public String getTxt16() {
        return txt16;
    }

    public Content txt16(String txt16) {
        this.txt16 = txt16;
        return this;
    }

    public void setTxt16(String txt16) {
        this.txt16 = txt16;
    }

    public String getTxt17() {
        return txt17;
    }

    public Content txt17(String txt17) {
        this.txt17 = txt17;
        return this;
    }

    public void setTxt17(String txt17) {
        this.txt17 = txt17;
    }

    public String getTxt18() {
        return txt18;
    }

    public Content txt18(String txt18) {
        this.txt18 = txt18;
        return this;
    }

    public void setTxt18(String txt18) {
        this.txt18 = txt18;
    }

    public String getTxt19() {
        return txt19;
    }

    public Content txt19(String txt19) {
        this.txt19 = txt19;
        return this;
    }

    public void setTxt19(String txt19) {
        this.txt19 = txt19;
    }

    public String getTxt20() {
        return txt20;
    }

    public Content txt20(String txt20) {
        this.txt20 = txt20;
        return this;
    }

    public void setTxt20(String txt20) {
        this.txt20 = txt20;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public Content num1(BigDecimal num1) {
        this.num1 = num1;
        return this;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public Content num2(BigDecimal num2) {
        this.num2 = num2;
        return this;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

    public BigDecimal getNum3() {
        return num3;
    }

    public Content num3(BigDecimal num3) {
        this.num3 = num3;
        return this;
    }

    public void setNum3(BigDecimal num3) {
        this.num3 = num3;
    }

    public BigDecimal getNum4() {
        return num4;
    }

    public Content num4(BigDecimal num4) {
        this.num4 = num4;
        return this;
    }

    public void setNum4(BigDecimal num4) {
        this.num4 = num4;
    }

    public BigDecimal getNum5() {
        return num5;
    }

    public Content num5(BigDecimal num5) {
        this.num5 = num5;
        return this;
    }

    public void setNum5(BigDecimal num5) {
        this.num5 = num5;
    }

    public BigDecimal getNum6() {
        return num6;
    }

    public Content num6(BigDecimal num6) {
        this.num6 = num6;
        return this;
    }

    public void setNum6(BigDecimal num6) {
        this.num6 = num6;
    }

    public BigDecimal getNum7() {
        return num7;
    }

    public Content num7(BigDecimal num7) {
        this.num7 = num7;
        return this;
    }

    public void setNum7(BigDecimal num7) {
        this.num7 = num7;
    }

    public BigDecimal getNum8() {
        return num8;
    }

    public Content num8(BigDecimal num8) {
        this.num8 = num8;
        return this;
    }

    public void setNum8(BigDecimal num8) {
        this.num8 = num8;
    }

    public BigDecimal getNum9() {
        return num9;
    }

    public Content num9(BigDecimal num9) {
        this.num9 = num9;
        return this;
    }

    public void setNum9(BigDecimal num9) {
        this.num9 = num9;
    }

    public BigDecimal getNum10() {
        return num10;
    }

    public Content num10(BigDecimal num10) {
        this.num10 = num10;
        return this;
    }

    public void setNum10(BigDecimal num10) {
        this.num10 = num10;
    }

    public BigDecimal getNum11() {
        return num11;
    }

    public Content num11(BigDecimal num11) {
        this.num11 = num11;
        return this;
    }

    public void setNum11(BigDecimal num11) {
        this.num11 = num11;
    }

    public BigDecimal getNum12() {
        return num12;
    }

    public Content num12(BigDecimal num12) {
        this.num12 = num12;
        return this;
    }

    public void setNum12(BigDecimal num12) {
        this.num12 = num12;
    }

    public BigDecimal getNum13() {
        return num13;
    }

    public Content num13(BigDecimal num13) {
        this.num13 = num13;
        return this;
    }

    public void setNum13(BigDecimal num13) {
        this.num13 = num13;
    }

    public BigDecimal getNum14() {
        return num14;
    }

    public Content num14(BigDecimal num14) {
        this.num14 = num14;
        return this;
    }

    public void setNum14(BigDecimal num14) {
        this.num14 = num14;
    }

    public BigDecimal getNum15() {
        return num15;
    }

    public Content num15(BigDecimal num15) {
        this.num15 = num15;
        return this;
    }

    public void setNum15(BigDecimal num15) {
        this.num15 = num15;
    }

    public BigDecimal getNum16() {
        return num16;
    }

    public Content num16(BigDecimal num16) {
        this.num16 = num16;
        return this;
    }

    public void setNum16(BigDecimal num16) {
        this.num16 = num16;
    }

    public BigDecimal getNum17() {
        return num17;
    }

    public Content num17(BigDecimal num17) {
        this.num17 = num17;
        return this;
    }

    public void setNum17(BigDecimal num17) {
        this.num17 = num17;
    }

    public BigDecimal getNum18() {
        return num18;
    }

    public Content num18(BigDecimal num18) {
        this.num18 = num18;
        return this;
    }

    public void setNum18(BigDecimal num18) {
        this.num18 = num18;
    }

    public BigDecimal getNum19() {
        return num19;
    }

    public Content num19(BigDecimal num19) {
        this.num19 = num19;
        return this;
    }

    public void setNum19(BigDecimal num19) {
        this.num19 = num19;
    }

    public BigDecimal getNum20() {
        return num20;
    }

    public Content num20(BigDecimal num20) {
        this.num20 = num20;
        return this;
    }

    public void setNum20(BigDecimal num20) {
        this.num20 = num20;
    }

    public Instant getDate1() {
        return date1;
    }

    public Content date1(Instant date1) {
        this.date1 = date1;
        return this;
    }

    public void setDate1(Instant date1) {
        this.date1 = date1;
    }

    public Instant getDate2() {
        return date2;
    }

    public Content date2(Instant date2) {
        this.date2 = date2;
        return this;
    }

    public void setDate2(Instant date2) {
        this.date2 = date2;
    }

    public Instant getDate3() {
        return date3;
    }

    public Content date3(Instant date3) {
        this.date3 = date3;
        return this;
    }

    public void setDate3(Instant date3) {
        this.date3 = date3;
    }

    public Instant getDate4() {
        return date4;
    }

    public Content date4(Instant date4) {
        this.date4 = date4;
        return this;
    }

    public void setDate4(Instant date4) {
        this.date4 = date4;
    }

    public Instant getDate5() {
        return date5;
    }

    public Content date5(Instant date5) {
        this.date5 = date5;
        return this;
    }

    public void setDate5(Instant date5) {
        this.date5 = date5;
    }

    public Instant getDate6() {
        return date6;
    }

    public Content date6(Instant date6) {
        this.date6 = date6;
        return this;
    }

    public void setDate6(Instant date6) {
        this.date6 = date6;
    }

    public Instant getDate7() {
        return date7;
    }

    public Content date7(Instant date7) {
        this.date7 = date7;
        return this;
    }

    public void setDate7(Instant date7) {
        this.date7 = date7;
    }

    public Instant getDate8() {
        return date8;
    }

    public Content date8(Instant date8) {
        this.date8 = date8;
        return this;
    }

    public void setDate8(Instant date8) {
        this.date8 = date8;
    }

    public Instant getDate9() {
        return date9;
    }

    public Content date9(Instant date9) {
        this.date9 = date9;
        return this;
    }

    public void setDate9(Instant date9) {
        this.date9 = date9;
    }

    public Instant getDate10() {
        return date10;
    }

    public Content date10(Instant date10) {
        this.date10 = date10;
        return this;
    }

    public void setDate10(Instant date10) {
        this.date10 = date10;
    }

    public Instant getBool1() {
        return bool1;
    }

    public Content bool1(Instant bool1) {
        this.bool1 = bool1;
        return this;
    }

    public void setBool1(Instant bool1) {
        this.bool1 = bool1;
    }

    public Instant getBool2() {
        return bool2;
    }

    public Content bool2(Instant bool2) {
        this.bool2 = bool2;
        return this;
    }

    public void setBool2(Instant bool2) {
        this.bool2 = bool2;
    }

    public Instant getBool3() {
        return bool3;
    }

    public Content bool3(Instant bool3) {
        this.bool3 = bool3;
        return this;
    }

    public void setBool3(Instant bool3) {
        this.bool3 = bool3;
    }

    public Instant getBool4() {
        return bool4;
    }

    public Content bool4(Instant bool4) {
        this.bool4 = bool4;
        return this;
    }

    public void setBool4(Instant bool4) {
        this.bool4 = bool4;
    }

    public Instant getBool5() {
        return bool5;
    }

    public Content bool5(Instant bool5) {
        this.bool5 = bool5;
        return this;
    }

    public void setBool5(Instant bool5) {
        this.bool5 = bool5;
    }

    public CheckScript getCheckScript() {
        return checkScript;
    }

    public Content checkScript(CheckScript checkScript) {
        this.checkScript = checkScript;
        return this;
    }

    public void setCheckScript(CheckScript checkScript) {
        this.checkScript = checkScript;
    }

    public Flow getFlow() {
        return flow;
    }

    public Content flow(Flow flow) {
        this.flow = flow;
        return this;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public Task getTask() {
        return task;
    }

    public Content task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskExecution getTaskExecution() {
        return taskExecution;
    }

    public Content taskExecution(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
        return this;
    }

    public void setTaskExecution(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
    }

    public FlowExecution getFlowExecution() {
        return flowExecution;
    }

    public Content flowExecution(FlowExecution flowExecution) {
        this.flowExecution = flowExecution;
        return this;
    }

    public void setFlowExecution(FlowExecution flowExecution) {
        this.flowExecution = flowExecution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        return id != null && id.equals(((Content) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
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
