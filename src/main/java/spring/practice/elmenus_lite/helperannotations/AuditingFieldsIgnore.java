package spring.practice.elmenus_lite.helperannotations;

import org.mapstruct.Mapping;

@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "updatedAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "updatedBy", ignore = true)
public @interface AuditingFieldsIgnore {
}
