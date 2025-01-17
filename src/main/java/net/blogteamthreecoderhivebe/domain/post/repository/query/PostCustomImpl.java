package net.blogteamthreecoderhivebe.domain.post.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.blogteamthreecoderhivebe.domain.member.constant.ApplyResult;
import net.blogteamthreecoderhivebe.domain.post.constant.PostCategory;
import net.blogteamthreecoderhivebe.domain.post.constant.PostStatus;
import net.blogteamthreecoderhivebe.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.blogteamthreecoderhivebe.domain.member.entity.QApplyInfo.applyInfo;
import static net.blogteamthreecoderhivebe.domain.post.entity.QPost.post;
import static net.blogteamthreecoderhivebe.domain.post.entity.QRecruitJob.recruitJob;
import static net.blogteamthreecoderhivebe.domain.post.entity.QRecruitSkill.recruitSkill;

@RequiredArgsConstructor
public class PostCustomImpl implements PostCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<ApplyResult, List<Post>> memberApplyPost(Long memberId) {
        List<Tuple> result = queryFactory
                .select(post, applyInfo)
                .from(applyInfo)
                .join(applyInfo.recruitJob, recruitJob)
                .join(recruitJob.post, post)
                .where(
                        applyInfo.member.id.eq(memberId)
                )
                .fetch();

        Map<ApplyResult, List<Post>> posts = new HashMap<>();

        List<Post> passPost = new ArrayList<>();
        List<Post> nonPost = new ArrayList<>();
        for (Tuple tuple : result) {
            System.out.println(tuple.get(applyInfo.applyResult));
            if (tuple.get(applyInfo.applyResult) == ApplyResult.APPLY) {
                nonPost.add(tuple.get(post));
            } else if (tuple.get(applyInfo.applyResult) == ApplyResult.PASS) {
                passPost.add(tuple.get(post));
            }
        }
        posts.put(ApplyResult.APPLY, nonPost);
        posts.put(ApplyResult.PASS, passPost);
        return posts;
    }

    @Override
    public Page<Post> findPosts(PostCategory category,
                                PostStatus status,
                                List<Long> locations,
                                List<Long> jobs,
                                Pageable pageable) {

        List<Post> posts = queryFactory
                .selectFrom(post)
                .leftJoin(post.recruitJobs, recruitJob)
                .leftJoin(post.recruitSkills, recruitSkill)
                .where(
                        eqPostCategory(category),
                        eqPostStatus(status),
                        inLocations(locations),
                        inJobs(jobs)
                )
                .groupBy(post.id)
                .offset(pageable.getOffset()) // 어디서부터 보여줄 건지
                .limit(pageable.getPageSize()) // 한 페이지에 보여줄 개수
                .orderBy(post.modifiedAt.desc()) // 가장 최근 글이 맨 앞에 오도록 정렬
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, countQuery()::fetchOne);
    }

    /**
     * 총 검색 결과 개수
     */
    private JPAQuery<Long> countQuery() {
        return queryFactory
                .select(post.count())
                .from(post);
    }

    /**
     * 카테고리 검색 조건
     */
    private BooleanExpression eqPostCategory(PostCategory postCategory) {
        return postCategory != null ? post.postCategory.eq(postCategory): null;
    }

    /**
     * 모집상태 검색 조건
     */
    private BooleanExpression eqPostStatus(PostStatus postStatus) {
        return postStatus != null ? post.postStatus.eq(postStatus) : null;
    }

    /**
     * 지역 검색 조건
     */
    private BooleanExpression inLocations(List<Long> locations) {
        return locations != null ? post.location.id.in(locations) : null;
    }

    /**
     * 직무 검색 조건
     */
    private BooleanExpression inJobs(List<Long> jobs) {
        return jobs != null ? recruitJob.job.id.in(jobs) : null;
    }
}


