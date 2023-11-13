package christmas.controller;

import christmas.view.InputView;

public class EventPlanner {
    private final InputView inputView;

    public EventPlanner(InputView inputView) {
        this.inputView = inputView;
    }

    public void planEvent() {
        int visitDay = inputView.readVisitDay();
    }
}
