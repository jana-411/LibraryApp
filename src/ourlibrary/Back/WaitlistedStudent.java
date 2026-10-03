package ourlibrary.Back;

import java.time.LocalDateTime;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class WaitlistedStudent implements Comparable<WaitlistedStudent> {

    private Student student;
    private LocalDateTime requestTime;

    public WaitlistedStudent(Student student) {
        this.student = student;
        this.requestTime = LocalDateTime.now();
    }

    public Student getStudent() {
        return student;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    @Override
    public int compareTo(WaitlistedStudent other) {
        // Graduate students have higher priority (come first)
        boolean thisGrad = this.student.getStatue().equalsIgnoreCase("graduate");
        boolean otherGrad = other.student.getStatue().equalsIgnoreCase("graduate");

        if (thisGrad && !otherGrad) {
            return -1;       // this is graduate, other is not → this first
        }
        if (!thisGrad && otherGrad) {
            return 1;        // other is graduate, this is not → other first
        }
        // Both same status → earlier request time first
        return this.requestTime.compareTo(other.requestTime);
    }
}
