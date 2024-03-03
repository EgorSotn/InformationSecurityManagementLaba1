package org.example.laba.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.laba.domain.EncodingKey;
import org.example.laba.exception.NotFoundException;
import org.example.laba.repository.EncodingKeyRepository;
import org.example.laba.service.EncodingKeyService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncodingKeyServiceImpl implements EncodingKeyService {
    private final EncodingKeyRepository encodingKeyRepository;

    @Override
    public String createKey(String key) {
        encodingKeyRepository.deleteAll(encodingKeyRepository.findAll());
        return encodingKeyRepository.save(new EncodingKey(key)).getKey();
    }

    @Override
    public String getKey() {
        return encodingKeyRepository.findAll().stream().findFirst().orElseThrow(()-> new NotFoundException("key not found")).getKey();
    }
}
