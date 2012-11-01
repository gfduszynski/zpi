/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import org.chessclan.dataTier.models.Result;

/**
 *
 * @author Xcays
 */
public interface ResultsBO{
    public Result saveResult(Result r);
    public Iterable<Result> saveResults(Iterable<Result> r);
    public Result findResultById(int id);
    public Iterable<Result> findResultsById(Iterable<Integer> ids);
    public Iterable<Result> findAll();
    public void deleteResult(int id);
    public void deleteResult(Result r);
    public void deleteResults(Iterable<Result> rs);
}
