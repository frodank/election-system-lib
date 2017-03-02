/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.Objects;
import temp.frodank.electionsystem.Choice;

/**
 *
 * @author frodank
 */
public class TestChoice implements Choice<TestChoice> {

    private String name;

    public TestChoice(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(TestChoice o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestChoice other = (TestChoice) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}