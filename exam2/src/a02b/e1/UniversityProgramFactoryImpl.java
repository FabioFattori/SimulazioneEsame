package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private UniversityProgram createGeneric(BiPredicate<HashMap<String, Pair<Sector, Integer>>, Set<String>> validate) {
        return new UniversityProgram() {

            HashMap<String, Pair<Sector, Integer>> table = new HashMap<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                table.put(name, new Pair<UniversityProgram.Sector, Integer>(sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                return validate.test(table, courseNames);
            }

        };
    }

    @Override
    public UniversityProgram flexible() {
        return this.createGeneric((table, set) -> calcOverall(table, set) == 60);
    }

    private int calcOverall(HashMap<String, Pair<Sector, Integer>> table, Set<String> courses) {
        int tot = 0;
        for (String course : courses) {
            tot += table.get(course).get2();
        }

        return tot;
    }

    private int calcCreditsOfSector(HashMap<String, Pair<Sector, Integer>> table, Set<String> courses, Sector s) {
        int tot = 0;
        for (String course : courses) {
            if (table.get(course).get1() == s) {
                tot += table.get(course).get2();
            }
        }

        return tot;
    }

    @Override
    public UniversityProgram scientific() {
        return this.createGeneric((table, set) -> calcOverall(table, set) == 60
                && calcCreditsOfSector(table, set, Sector.MATHEMATICS) >= 12
                && calcCreditsOfSector(table, set, Sector.COMPUTER_SCIENCE) >= 12
                && calcCreditsOfSector(table, set, Sector.PHYSICS) >= 12);
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return this.createGeneric((table, set) -> calcOverall(table, set) >= 48
                && calcCreditsOfSector(table, set, Sector.COMPUTER_SCIENCE)
                        + calcCreditsOfSector(table, set, Sector.COMPUTER_ENGINEERING) >= 30);
    }

    @Override
    public UniversityProgram realistic() {
        return this.createGeneric((table, set) -> calcOverall(table, set) == 120
                && calcCreditsOfSector(table, set, Sector.COMPUTER_SCIENCE)
                        + calcCreditsOfSector(table, set, Sector.COMPUTER_ENGINEERING) >= 60
                && calcCreditsOfSector(table, set, Sector.PHYSICS)
                        + calcCreditsOfSector(table, set, Sector.MATHEMATICS) <= 18
                && calcCreditsOfSector(table, set, Sector.THESIS) == 24);
    }

}
