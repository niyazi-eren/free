package odine.freelancermarketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "DESIGNER")
public class Designer extends Freelancer {

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "freelancers_design_tools",
            joinColumns = @JoinColumn(name = "freelancer_id"),
            inverseJoinColumns = @JoinColumn(name = "design_tool_id")
    )
    private Set<DesignTool> designTools = new HashSet<>();

    public void addDesignTool(DesignTool designTool) {
        designTools.add(designTool);
        designTool.getDesigners().add(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
