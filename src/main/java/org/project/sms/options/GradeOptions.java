package org.project.sms.options;


public enum GradeOptions {
    GRADE_1,
    GRADE_2,
    GRADE_3,
    GRADE_4,
    GRADE_5,
    GRADE_6,
    GRADE_7,
    GRADE_8,
    GRADE_9,
    GRADE_10,
    GRADE_11,
    GRADE_12;

    public String toString() {
        return name().replace("_"," ");
    }

}



