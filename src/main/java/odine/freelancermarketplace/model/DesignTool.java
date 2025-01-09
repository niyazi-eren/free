package odine.freelancermarketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "design_tools")
public class DesignTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "designTools")
    @JsonIgnore
    @ToString.Exclude
    private Set<Designer> designers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesignTool designTool = (DesignTool) o;
        return Objects.equals(name, designTool.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
