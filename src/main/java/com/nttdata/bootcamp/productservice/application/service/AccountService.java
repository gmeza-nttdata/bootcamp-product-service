package com.nttdata.bootcamp.productservice.application.service;

import com.nttdata.bootcamp.productservice.domain.entity.Account;
import org.springframework.stereotype.Component;

/** This class implements CRUD methods.
 *
 * @author gmeza
 *
 */
@Component
public interface AccountService extends IService<Account, String> {


}
