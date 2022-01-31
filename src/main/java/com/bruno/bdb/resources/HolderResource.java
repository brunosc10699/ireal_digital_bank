package com.bruno.bdb.resources;

import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.dto.NewHolderDTO;
import com.bruno.bdb.services.HolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/holders")
@RequiredArgsConstructor
public class HolderResource {

    private final HolderService holderService;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody NewHolderDTO newHolderDTO) {
        Holder user = holderService.save(newHolderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
