package com.ainapapy.aigle.models.convertors;

import com.ainapapy.aigle.models.Group;
import com.ainapapy.aigle.models.dto.GroupDTO;
import com.ainapapy.aigle.repositories.GroupRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupConvertor {
    
    @Autowired
    private GroupRepository groupRepository;
    
    public Group saveOrUpdateGroup(Group group) {
        // Check if the group already exists in the database
        Optional<Group> existingGroup = groupRepository.findByName(group.getName());
        if (existingGroup.isPresent()) {
            return existingGroup.get(); // Return the existing group
        }
        return groupRepository.save(group); // Save the new group
    }
    
    public GroupDTO convertToDTO(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        return dto;
    } 
    
}
