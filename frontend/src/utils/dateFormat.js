/**
 * 날짜 포맷 유틸리티 함수
 */

/**
 * 날짜를 YYYY.MM.DD 형식으로 포맷팅
 * @param {string|Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} - YYYY.MM.DD 형식의 문자열 (예: 2024.09.01)
 */
export const formatDate = (date) => {
  if (!date) return '';
  
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  
  return `${year}.${month}.${day}`;
};

/**
 * 날짜를 YYYY.MM 형식으로 포맷팅
 * @param {string|Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} - YYYY.MM 형식의 문자열 (예: 2024.09)
 */
export const formatYearMonth = (date) => {
  if (!date) return '';
  
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  
  return `${year}.${month}`;
};

/**
 * 생년월일을 한국어 형식으로 포맷팅
 * @param {string|Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} - 한국어 날짜 (예: 1990년 1월 1일)
 */
export const formatBirthDate = (date) => {
  if (!date) return '';
  
  const d = new Date(date);
  const year = d.getFullYear();
  const month = d.getMonth() + 1;
  const day = d.getDate();
  
  return `${year}년 ${month}월 ${day}일`;
};
