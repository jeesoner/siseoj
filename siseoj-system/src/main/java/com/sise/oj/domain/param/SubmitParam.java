package com.sise.oj.domain.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class SubmitParam implements Serializable {

    private List<Long> submitIds;
}
