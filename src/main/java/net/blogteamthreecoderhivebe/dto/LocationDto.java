package net.blogteamthreecoderhivebe.dto;

import lombok.Builder;
import net.blogteamthreecoderhivebe.entity.Location;

@Builder
public record LocationDto(
        Long id,
        String region
) {
    public static LocationDto from(Location Entity) {
        return LocationDto.builder()
                .id(Entity.getId())
                .region(Entity.getRegion())
                .build();
    }

    public Location toEntity() {
        return Location.builder()
                .id(id)
                .region(region)
                .build();
    }

}
