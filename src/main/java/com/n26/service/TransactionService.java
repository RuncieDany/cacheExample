package com.n26.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.cache.TransactionCache;
import com.n26.exception.Past60Seconds;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.util.CacheKeyGenerator;
import com.n26.util.TimeCalculator;

@Service
public class TransactionService {

	@Autowired
	private TransactionCache transactionCache;
	@Autowired
	private Statistics statistics;
	
	public Statistics getAllStatistics() {

		statistics.setAvg(transactionCache.getAvg());
		statistics.setCount(transactionCache.getCount());
		statistics.setMax(transactionCache.getMax());
		statistics.setMin(transactionCache.getMin());
		statistics.setSum(transactionCache.getSum());

		return statistics;
	}

	public void deleteAllTransaction() {

		transactionCache.clear();

	}

	public void postTransaction(Transaction transaction) throws Exception {

		Long diffwithSystime = TimeCalculator.instantDifference(transaction.getTimestamp());

		if (diffwithSystime <= 60000L) {
			transactionCache.add(CacheKeyGenerator.getKey(), transaction.getAmount(), 60000L - diffwithSystime);

		}
		else {
			throw new Past60Seconds();
		}

	}

}
