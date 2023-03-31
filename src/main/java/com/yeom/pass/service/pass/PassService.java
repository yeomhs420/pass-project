package com.yeom.pass.service.pass;

import com.yeom.pass.repository.pass.PassEntity;
import com.yeom.pass.repository.pass.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassService {
    private final PassRepository passRepository;

    public List<Pass> getPasses(final String userId) {
        final List<PassEntity> passEntities = passRepository.findByUserId(userId);
        return PassModelMapper.INSTANCE.map(passEntities);

    }

}
