package pl.edu.agh.to2.hotel.presenter;

import pl.edu.agh.to2.hotel.service.IllegalOperationException;

@FunctionalInterface
public interface ActionFunction {
    void execute() throws IllegalOperationException;
}