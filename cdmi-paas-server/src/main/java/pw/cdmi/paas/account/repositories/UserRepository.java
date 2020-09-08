package pw.cdmi.paas.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pw.cdmi.paas.account.model.entities.UserAccount;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.open.model.entities.People;
import pw.cdmi.paas.account.model.entities.UserAccount;

public interface UserRepository extends PagingAndSortingRepository<UserAccount, String>, QueryByExampleExecutor<UserAccount> {

}
