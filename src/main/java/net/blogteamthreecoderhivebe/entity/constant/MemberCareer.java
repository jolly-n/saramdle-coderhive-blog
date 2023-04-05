package net.blogteamthreecoderhivebe.entity.constant;

import lombok.Getter;

public enum MemberCareer {
    NON("미지정"), ASSOCIATE("1~3년차"), JUNIOR("3~5년차"), SENIOR("5~10년차"), PRINCIPAL("10년차 이상");

    @Getter
    private final String description;

    MemberCareer(String description) {
        this.description = description;
    }

}
