package com.geostar.cloud.sysadmin.organization.entity.param;

import com.geostar.cloud.common.web.entity.param.BaseParam;
import com.geostar.cloud.sysadmin.organization.entity.po.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionQueryParam extends BaseParam<Position> {
    private String name;
}
