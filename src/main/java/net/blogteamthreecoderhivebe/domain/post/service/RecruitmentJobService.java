package net.blogteamthreecoderhivebe.domain.post.service;

import lombok.RequiredArgsConstructor;
import net.blogteamthreecoderhivebe.domain.info.service.JobService;
import net.blogteamthreecoderhivebe.domain.post.dto.request.RecruitmentJobRequestDto;
import net.blogteamthreecoderhivebe.domain.post.entity.Post;
import net.blogteamthreecoderhivebe.domain.post.entity.RecruitmentJob;
import net.blogteamthreecoderhivebe.domain.post.repository.RecruitmentJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RecruitmentJobService {
    private final JobService jobService;
    private final RecruitmentJobRepository recruitmentJobRepository;

    public void save(List<RecruitmentJobRequestDto.Save> dtos, Post post) {
        dtos.stream()
                .map(dto -> makeRecruitmentJob(dto, post))
                .forEach(recruitmentJobRepository::save);
    }

    private RecruitmentJob makeRecruitmentJob(RecruitmentJobRequestDto.Save dto, Post post) {
        return RecruitmentJob.builder()
                .post(post)
                .job(jobService.findOne(dto.jobId()))
                .number(dto.number())
                .build();
    }
}
