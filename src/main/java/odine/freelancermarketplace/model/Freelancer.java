package odine.freelancermarketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import odine.freelancermarketplace.enums.FreelancerType;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "freelancers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "freelancer_type", discriminatorType = DiscriminatorType.STRING)
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String city;

    @Column(name = "evaluation_score")
    private int evaluationScore;

    @Column(name = "freelancer_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private FreelancerType freelancerType;

    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Job> jobs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freelancer freelancer = (Freelancer) o;
        return Objects.equals(id, freelancer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
