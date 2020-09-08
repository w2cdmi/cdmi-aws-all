package pw.cdmi.wishlist.service.impl;

import pw.cdmi.wishlist.model.entities.Participant;
import pw.cdmi.wishlist.service.CrowdFundingService;

public class CrowdFundingServiceImpl implements CrowdFundingService {

	@Override
	public void addOnlooker(String wxUserId, String ownerId, String productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isJoin(String userId, String productId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Participant getParticipant(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long updateInviterNumberOfParticipant(Participant participant) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long updateInviterNumberOfParticipant(String participantId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addParticipant(String userId, String productId) {
		// TODO Auto-generated method stub
		
	}

}
