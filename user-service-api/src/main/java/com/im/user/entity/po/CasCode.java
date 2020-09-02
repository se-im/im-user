package com.im.user.entity.po;

import lombok.Data;

/**
 * Table: cas_code
 */
@Data
public class CasCode
{
    /**
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * Column:    cas
     * Nullable:  true
     */
    private String cas;

    /**
     * Column:    anwser
     * Nullable:  true
     */
    private String anwser;
}