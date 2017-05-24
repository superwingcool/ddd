package com.thoughtworks.devcloud.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AbstractRankTest {

    @Test
    public void equalsShouldReturnTrueGivenSameObject() {
        AbstractRank abstractRank = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        assertThat(abstractRank.equals(abstractRank), is(true));
    }

    @Test
    public void equalsShouldReturnFalseGivenDifferentObject() {
        AbstractRank abstractRank = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        assertThat(abstractRank.equals(new Object()), is(false));
    }

    @Test
    public void equalsShouldReturnFalseGivenDifferentRepoName() {
        AbstractRank abstractRank1 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank1.setRepoName("1");

        AbstractRank abstractRank2 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank2.setRepoName("2");

        assertThat(abstractRank1.equals(abstractRank2), is(false));
    }

    @Test
    public void equalsShouldReturnFalseGivenDifferentTaskName() {
        AbstractRank abstractRank1 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank1.setRepoName("1");
        abstractRank1.setTaskName("1");

        AbstractRank abstractRank2 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank2.setRepoName("1");
        abstractRank2.setTaskName("2");

        assertThat(abstractRank1.equals(abstractRank2), is(false));
    }

    @Test
    public void equalsShouldReturnTrueGivenRightData() {
        AbstractRank abstractRank1 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank1.setRepoName("1");
        abstractRank1.setTaskName("1");

        AbstractRank abstractRank2 = new AbstractRank() {
            @Override
            public int compareTo(AbstractRank o) {
                return 0;
            }
        };
        abstractRank2.setRepoName("1");
        abstractRank2.setTaskName("1");

        assertThat(abstractRank1.equals(abstractRank2), is(true));
    }


}