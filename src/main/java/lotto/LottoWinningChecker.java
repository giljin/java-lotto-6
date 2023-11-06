package lotto;

import java.util.List;

public class LottoWinningChecker {
    private final List<LottoNumber> lottoResult;
    private final LottoNumber bonusNumber;

    LottoWinningChecker(List<Integer> lottoWinningNumber, Integer bonusNumber) {
        validate(lottoWinningNumber);
        validateDuplicate(lottoWinningNumber);
        validateDuplicateBonusNumber(lottoWinningNumber, bonusNumber);
        this.lottoResult = lottoWinningNumber.stream()
                .map(LottoNumber::new)
                .toList();

        this.bonusNumber = new LottoNumber(bonusNumber);
    }
    private void validateDuplicateBonusNumber(List<Integer> lottoWinningNumber, Integer bonusNumber){
        if( lottoWinningNumber.contains(bonusNumber)){
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        int distinctSize = (int) numbers.stream()
                .distinct()
                .count();
        if (numbers.size() != distinctSize) {
            throw new IllegalArgumentException("[ERROR] 중복된 숫자는 입력 하실 수 없습니다.");
        }
    }

    private void validate(List<Integer> lottoWinningNumber) {
        if (lottoWinningNumber.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호 갯수는 6개 입니다.");
        }
    }

    private int matchCount(Lotto lotto) {
        int matchCount = 0;
        for (LottoNumber lottoNumber : lottoResult) {
            if (lotto.containLottoNumber(lottoNumber)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    public LottoResult check(Lotto lotto) {
        int matchCount = matchCount(lotto);
        boolean containBonusNumber = lotto.containLottoNumber(bonusNumber);

        return LottoRule.of(matchCount, containBonusNumber);
    }
}
