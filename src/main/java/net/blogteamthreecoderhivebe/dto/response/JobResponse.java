package net.blogteamthreecoderhivebe.dto.response;

import lombok.Builder;
import net.blogteamthreecoderhivebe.dto.JobDto;

@Builder
public record JobResponse(
        String main,
        String detail
) {
    public static JobResponse from(JobDto dto) {
        return JobResponse.builder()
                .main(dto.main())
                .detail(dto.detail())
                .build();
    }

    public JobDto toDto() {
        return JobDto.builder()
                .main(main)
                .detail(detail)
                .build();
    }
}
